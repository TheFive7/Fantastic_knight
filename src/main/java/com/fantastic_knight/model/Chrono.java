package com.fantastic_knight.model;

import com.fantastic_knight.Player;

class Chrono implements Runnable {
    Item item;
    Player player;
    String methode;

    public Chrono(Item item, Player player, String methode)
    {
        this.item = item;
        this.player = player;
        this.methode = methode;

    }

    public void run() {
        // Créer un switch pour savoir quoi mettre en attente
        try {
            this.item.isActive = false;     // désactive le piège
            choixMethodes(methode);         // choix de la méthode à utiliser selon l'item
            Thread.sleep(3000);       // attend 3000 millisecondes -> 3 secondes
            this.item.isActive = true;      // réactive l'item avant de terminer le Thread puis le fermer
            Thread.currentThread().interrupt();
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void choixMethodes(String methode){
        switch (methode){
            case "spikes":
                if (player.isLife()) player.setLife(false);
                else player.reset();
                break;
            case "shield":
                System.out.println("Me voilà bien protégé");
        }
    }
}