package co.uk.jedpalmer.thesource.utils.player;

/**
 * Created by peraldon on 30/06/2015.
 */
public enum MessageTypes {
    /**
     * Success - Generally green etc (Successfully bought item x from the store!)
     * Failure - Ingame failure without error (You do not have enough money to purchase this item!)
     * Information - General info for the player (We have a TeamSpeak server!)
     * Error - When something goes wrong
     */
    SUCCESS, FAILURE, INFORMATION, ERROR
}

