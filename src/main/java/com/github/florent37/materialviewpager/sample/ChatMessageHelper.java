package com.github.florent37.materialviewpager.sample;

import java.util.ArrayList;


public class ChatMessageHelper {

    private ArrayList<SingleConversation> conversations;
    private int currentConversationIndex;

    SingleConversation ageConv, retireAgeConv, retireIncomeConv, currentSavingsConv, retirementContributionConv,
                        adviserConv, goodByeConv;

    ChatMessageHelper() {
        currentConversationIndex = 0;

        //Will add all the conversations here
        ageConv = new SingleConversation(false, true);
        ageConv.setRange(30, 55);
        ageConv.addBotMessage("Hello Lana... Good to see you here");
        ageConv.addBotMessage("iRetire is custom tailored for your retirement needs");
        ageConv.addBotMessage("Let's plan for your retirement");
        ageConv.addBotMessage("What's your age?");
        ageConv.addNegativeMessage("We need your current age to proceed. Please tell us your age");
        ageConv.addNegativeMessage("Don't worry, we will keep it a secret :). Please tell us your age");

        retireAgeConv = new SingleConversation(true, true);
        retireAgeConv.setRange(56, 100);
        retireAgeConv.addBotMessage("When are you planning to retire?");
        retireAgeConv.addNegativeMessage("Fair enough, let's assume that you are going to retire at age 60 for now");

        retireIncomeConv = new SingleConversation(false, true);
        retireIncomeConv.setRange(10000,1000000);
        retireIncomeConv.addBotMessage("What is your expected retirement income?");
        retireIncomeConv.addNegativeMessage("We were expecting a numerical response. Please tell us what is your expected retirement income?");
        retireIncomeConv.addNegativeMessage("We did not get that. Please tell us what is your expected retirement income in numerical form?");

        currentSavingsConv = new SingleConversation(true, true);
        currentSavingsConv.setRange(0, 10000000);
        currentSavingsConv.addBotMessage("How much retirement savings do you have currently?");
        currentSavingsConv.addNegativeMessage("We did not get you.. We are going to assume that you are freshly starting for retirement savings");

        retirementContributionConv = new SingleConversation(false, true);
        retirementContributionConv.setRange(0, 1000000);
        retirementContributionConv.addBotMessage("Finally, how much are you planning to contribute towards retirement each year?");
        retirementContributionConv.addNegativeMessage("This is the last piece of information. We won't bug you after this :). How much are you planning to save for retirement each year?");

        conversations = new ArrayList<SingleConversation>();
        conversations.add(ageConv);
        conversations.add(retireAgeConv);
        conversations.add(retireIncomeConv);
        conversations.add(currentSavingsConv);
        conversations.add(retirementContributionConv);

        adviserConv = new SingleConversation(true, false);
        adviserConv.addBotMessage("iRetire works best when your retirement planning is done with an adviser. Retirement advisers will collaborate with you to plan for the best possible retirement journey");
        adviserConv.addBotMessage("Would you like our retirement adviser to contact you?");

        goodByeConv = new SingleConversation(true, false);
        goodByeConv.addBotMessage("Thanks for spending time with us Lana");
        goodByeConv.addBotMessage("Have a nice day");
        goodByeConv.addBotMessage("And good luck planning for your retirement :)");
        goodByeConv.addBotMessage("Good Bye");

    }

    public boolean isConversationValid() {
        return (currentConversationIndex < conversations.size());
    }

    public SingleConversation getCurrentConversation() {
        return conversations.get(currentConversationIndex);
    }

    public void moveToNextConversation() {
        ++currentConversationIndex;
    }

    public String getChatResponses() {
        StringBuilder sb = new StringBuilder();
        for(SingleConversation conv : conversations) {
            sb.append(conv.getResponse()).append(",");
        }
        return sb.toString();
    }

    public String getRetirementIncomeString() {
        int age, retireAge, retireIncome, currentSavings, retirementContribution;
        age = conversations.get(0).getResponse();
        retireAge = conversations.get(1).getResponse();
        retireIncome = conversations.get(2).getResponse();
        currentSavings = conversations.get(3).getResponse();
        retirementContribution = conversations.get(4).getResponse();

        int timeToRetire = retireAge - age;
        double rateReturn = 0.05f;

        double resultIncome = currentSavings*Math.pow(1+rateReturn, timeToRetire);
        for(int i=timeToRetire-1; i>0; --i) {
            resultIncome += retirementContribution*Math.pow(1+rateReturn, i);
        }

        int maxAge = 105;

        int resultIncomeInt = (int) resultIncome/(maxAge-retireAge);
        if(resultIncomeInt >= retireIncome) {
            return "Congratulations!! Based on your current planning you should get a retirement income of \"" + resultIncomeInt + "\" which exceeds your input expectation " + retireIncome;
        }
        return "Based on your inputs you should get a retirement income of \"" + resultIncomeInt + "\" which falls short of your expectation of \"" + retireIncome + "\". Don't worry, our expert retirement advisers can help you out";
    }

    public SingleConversation getAdviserConv() {
        return adviserConv;
    }

    public SingleConversation getGoodByeConv() {
        return goodByeConv;
    }
}
