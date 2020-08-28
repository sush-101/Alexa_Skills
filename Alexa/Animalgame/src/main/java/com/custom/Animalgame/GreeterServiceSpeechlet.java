package com.custom.Animalgame;
import com.amazon.speech.slu.Slot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;



/**
* This sample shows how to create a Lambda function for handling Alexa Skill requests that:
 * @author tele
 *
 */
public class GreeterServiceSpeechlet implements SpeechletV2 {
private static final Logger log = LoggerFactory.getLogger(GreeterServiceSpeechlet.class);



@Override
public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
    log.info("onSessionStarted requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
            requestEnvelope.getSession().getSessionId());

    // any initialization logic goes here
}

@Override
public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
    log.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
            requestEnvelope.getSession().getSessionId());

    String speechOutput =
           "Welcome to Alexa Animal Game. Start the game with an animal name .";
    // If the user either does not reply to the welcome message or says
    // something that is not understood, they will be prompted again with this text.
    String repromptText = "For instructions on what you can say, please say help me.";

    // Here we are prompting the user for input
    return newAskResponse(speechOutput, repromptText);
    
}

@Override
public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
    IntentRequest request = requestEnvelope.getRequest();
    log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
            requestEnvelope.getSession().getSessionId());

    Intent intent = request.getIntent();
    String intentName = (intent != null) ? intent.getName() : null;

    if ("firstanimal".equals(intentName)) {
        return getHelloWorldIntent(intent);
    } else if ("AMAZON.HelpIntent".equals(intentName)) {
        return getHelp();
    } else if ("AMAZON.StopIntent".equals(intentName)) {
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText("Goodbye");

        return SpeechletResponse.newTellResponse(outputSpeech);
    } else if ("AMAZON.CancelIntent".equals(intentName)) {
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText("Goodbye");

        return SpeechletResponse.newTellResponse(outputSpeech);
    } else {
        String errorSpeech = "This is unsupported.  Please try something else.";
        return newAskResponse(errorSpeech, errorSpeech);
    }
}

@Override
public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
    log.info("onSessionEnded requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
            requestEnvelope.getSession().getSessionId());

    // any cleanup logic goes here
}

/**
 * Creates a {@code SpeechletResponse} for the RecipeIntent.
 *
 * @param intent
 *            intent for the request
 * @return SpeechletResponse spoken and visual response for the given intent
 */
private SpeechletResponse getHelloWorldIntent(Intent intent) {
        String responseText =null;
        Slot name=intent.getSlot("animal");
        String animalname=name.getValue();
        String b[]= {"zebra","cat","dog","donkey","duck","monkey","turtoise","lion","tiger","rat"};
        char x=animalname.charAt(animalname.length()-1);
        int j=0,flag=0;
        String a[]=new String[10];
        for(int i=0;i<b.length;i++) {
        	if(b[i].charAt(0)==x) {
        		for(int k=0;k<a.length;k++) {
        			if(b[i]==a[k]) {
        				flag=1;
        		}
        		}
        			if (flag==0) {
        		responseText = b[i];
        		a[j]=b[i];
        		j++;
        	}
        }
        }
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText(responseText);

            SimpleCard card = new SimpleCard();
            card.setTitle("Intent Values  ");
            card.setContent(responseText);
            PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
            repromptOutputSpeech.setText("se");
            Reprompt reprompt = new Reprompt();
            reprompt.setOutputSpeech(repromptOutputSpeech);

            return SpeechletResponse.newAskResponse(outputSpeech, reprompt,card);
        
   
     
}

/**
 * Creates a {@code SpeechletResponse} for the HelpIntent.
 *
 * @return SpeechletResponse spoken and visual response for the given intent
 */
private SpeechletResponse getHelp() {
    String speechOutput =
            "You can say wish my friend";
    String repromptText =
    		"You can say wish my friend";
    return newAskResponse(speechOutput, repromptText);
}

/**
 * Wrapper for creating the Ask response. The OutputSpeech and {@link Reprompt} objects are
 * created from the input strings.
 *
 * @param stringOutput
 *            the output to be spoken
 * @param repromptText
 *            the reprompt for if the user doesn't reply or is misunderstood.
 * @return SpeechletResponse the speechlet response
 */
private SpeechletResponse newAskResponse(String stringOutput, String repromptText) {
	
	 SimpleCard card = new SimpleCard();
     card.setTitle(stringOutput);
    PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
    outputSpeech.setText(stringOutput);

    PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
    repromptOutputSpeech.setText(repromptText);
    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(repromptOutputSpeech);

    return SpeechletResponse.newAskResponse(outputSpeech, reprompt,card);
}



}
