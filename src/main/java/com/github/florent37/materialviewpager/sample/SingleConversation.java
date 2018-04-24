package com.github.florent37.materialviewpager.sample;


import java.util.ArrayList;
import java.util.Scanner;

public class SingleConversation {
    ArrayList<String> botMessages;
    ArrayList<String> negativeMessages;
    boolean proceedWithNegative;
    boolean isNumericalResponse;
    int rangeLow;
    int rangeHigh;
    int response;

    SingleConversation(boolean proceedWithNegative, boolean isNumericalResponse) {
        botMessages = new ArrayList<String>();
        negativeMessages = new ArrayList<String>();

        this.proceedWithNegative = proceedWithNegative;
        this.isNumericalResponse = isNumericalResponse;
        rangeLow = 0;
        rangeHigh = -1;
        response = -1;
    }

    public void addBotMessage(String message) {
        botMessages.add(message);
    }

    public ArrayList<String> getBotMessages() {
        return botMessages;
    }

    public void addNegativeMessage(String message) {
        negativeMessages.add(message);
    }

    public String getNegativeMessage() {
        return negativeMessages.get(0);
    }

    public void setRange(int rangeLow, int rangeHigh) {
        this.rangeLow = rangeLow;
        this.rangeHigh = rangeHigh;
    }

    public boolean isInRange(int number) {
        return (number >= rangeLow && number <= rangeHigh);
    }

    public String outOfRangeMessage(int number) {
        if (number < rangeLow)
            return "The number you entered: " + number + " is too low. Accepted number lies between " + rangeLow + " and " + rangeHigh;
        return "The number you entered: " + number + " is too high. Accepted number lies between " + rangeLow + " and " + rangeHigh;
    }

    public int parseResponse(String response) {
        Scanner in = new Scanner(response).useDelimiter("[^0-9]+");
        int responseInteger = -1;
        try {
            responseInteger = in.nextInt();
        } catch (Exception e) {
            return responseInteger;
        }
        return responseInteger;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public int getResponse() {
        return response;
    }

}
