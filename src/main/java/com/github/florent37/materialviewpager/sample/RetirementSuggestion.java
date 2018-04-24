package com.github.florent37.materialviewpager.sample;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RetirementSuggestion extends AppCompatActivity {

    EditText chatMessageText;
    LinearLayout chatLayout;

    ChatMessageHelper chatHelper;

    boolean shownRetirementResults, shownAdviser, shownBye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retirement_suggestion);

        chatMessageText = (EditText) findViewById(R.id.chat_message_et);
        chatLayout = (LinearLayout) findViewById(R.id.chat_layout);

        shownRetirementResults = false;
        shownAdviser = false;
        shownBye = false;
        chatHelper = new ChatMessageHelper();
        showBotMessages();
        Log.i("GSK", "HI bot");
    }

    public void showBotMessages() {
        if(chatHelper.isConversationValid()) {
            SingleConversation currentConv = chatHelper.getCurrentConversation();
            Log.i("GSK", Integer.toString(currentConv.getBotMessages().size()));
            for(String botMessage : currentConv.getBotMessages()) {
                addMessage(botMessage, false);  //False as botMessage
            }
        } else {
            if(!shownRetirementResults) {
                //TODO - Show retirement results for now we are going to show getChatResponses message
                String iRetreResults = chatHelper.getRetirementIncomeString();
                addBoldMessage(iRetreResults);

                shownRetirementResults = true;
                showBotMessages();  //For Adviser
            } else if(!shownAdviser) {
                SingleConversation adviserConv = chatHelper.getAdviserConv();
                for(String botMessage : adviserConv.getBotMessages()) {
                    addMessage(botMessage, false);  //False as botMessage
                }
            } else if(!shownBye) {
                SingleConversation byeConv = chatHelper.getGoodByeConv();
                for(String botMessage : byeConv.getBotMessages()) {
                    addMessage(botMessage, false);  //False as botMessage
                }
                shownBye = true;
            }
        }
        //Chat has ended, so we will post other messages now
        Log.i("GSK", "General chat has ended");
    }

    public void chatSendClicked(View v) {
        String message = chatMessageText.getText().toString();
        addMessage(message, true);      //True as always user's message is going to be recieved from chatSendClicked

        chatMessageText.setText("");

        if(!shownRetirementResults) {
            SingleConversation currentConv = chatHelper.getCurrentConversation();
            int responseInteger = currentConv.parseResponse(message);

            if(currentConv.isInRange(responseInteger)){
                currentConv.setResponse(responseInteger);
                chatHelper.moveToNextConversation();
                showBotMessages();
            } else {
                if(responseInteger == -1)       // If not integer
                    addMessage(currentConv.getNegativeMessage(), false);  //False as botMessage
                else        // If there is integer, then it has come here because it was out of range
                    addMessage(currentConv.outOfRangeMessage(responseInteger), false);
            }
        } else if(!shownAdviser) {
            String lowerMessage = message.toLowerCase();
            int indexYes = lowerMessage.indexOf("yes");

            if(indexYes != -1) {
                addMessage("Great!! Our adviser will contact you soon", false);
            } else {
                addMessage("Seems like you are not interested in retirement planning now.. Maybe next time :)", false);
            }
            shownAdviser = true;
            showBotMessages();
        }
    }

    public void addBoldMessage(String message) {
        TextView chatMessageView = (TextView) LayoutInflater.from(this).inflate(R.layout.chat_message_left, chatLayout, false);

        chatMessageView.setText(message);
        chatMessageView.setTypeface(chatMessageView.getTypeface(), Typeface.BOLD);
        chatLayout.addView(chatMessageView);
        chatMessageView.requestFocus();

        chatMessageText.requestFocus();
    }

    public void addMessage(String message, boolean userMessage) {
        TextView chatMessageView;
        if(userMessage) {
            chatMessageView = (TextView) LayoutInflater.from(this).inflate(R.layout.chat_message_right, chatLayout, false);
        } else {
            chatMessageView = (TextView) LayoutInflater.from(this).inflate(R.layout.chat_message_left, chatLayout, false);
        }

        chatMessageView.setText(message);
        chatLayout.addView(chatMessageView);
        chatMessageView.requestFocus();

        chatMessageText.requestFocus();
    }
}
