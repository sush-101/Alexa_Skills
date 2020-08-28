package com.custom.gmail;
import org.slf4j.Logger;



import com.amazon.speech.slu.Slot;

import com.amazon.speech.speechlet.Session;

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
 * @author
 *
 */
public class GreeterServiceSpeechlet implements SpeechletV2 {
private static final Logger log = LoggerFactory.getLogger(GreeterServiceSpeechlet.class);
private static final String EMAILID_KEY= "emailid";
private static final String Subject_key= "subject";
private static final String BODY_key= "body";

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
    		"Welcome to Alexa Gmail Skill. You can send a mail.  "
                    + "Provide the email id of the recipient";
    // If the user either does not reply to the welcome message or says
    // something that is not understood, they will be prompted again with this text.
    String repromptText = "For instructions on what you can say, please say help me.";

    // Here we are prompting the user for input
    return newAskResponse(speechOutput, repromptText);
    
}

@Override
public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
    IntentRequest request = requestEnvelope.getRequest();
    Session session=requestEnvelope.getSession();
    log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
            requestEnvelope.getSession().getSessionId());

    Intent intent = request.getIntent();
    String intentName = (intent != null) ? intent.getName() : null;

    if ("emailidintent".equals(intentName)) {
        return getHelloWorldIntent(intent,session);
    }else if ("subjectintent".equals(intentName)) {
        return getsubject(intent,session);
    }else if ("bodyintent".equals(intentName)) {
        return getbody(intent,session);
    } else if ("sendmailintent".equals(intentName)) {
        return sendmail(intent,session);
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
 private SpeechletResponse getHelloWorldIntent(final Intent intent,final Session session) {
	 String responseText2=null;
		Slot nameSlot=intent.getSlot("emailid");
		session.setAttribute(EMAILID_KEY,nameSlot.getValue());
	   
	     		responseText2="Provide the Subject of the mail";
	            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
	            outputSpeech.setText(responseText2);
	            
	            String repromptText = "For instructions on what you can say, please say help me.";
	            PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
	            repromptOutputSpeech.setText(repromptText);
	            Reprompt reprompt = new Reprompt();
	            reprompt.setOutputSpeech(repromptOutputSpeech);
	            
	            SimpleCard card = new SimpleCard();
	            card.setTitle("Intent Values  ");
	            card.setContent(responseText2);

	            return SpeechletResponse.newAskResponse(outputSpeech,reprompt, card);
	           
}
private SpeechletResponse getsubject(final Intent intent,final Session session) {
	 String responseText=null;
		Slot nameSlot1=intent.getSlot("subject");
		session.setAttribute(Subject_key,nameSlot1.getValue());
	    
	     		responseText="Provide the Body of the mail";
	            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
	            outputSpeech.setText(responseText);
	            
	            String repromptText = "type body is";
	            PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
	            repromptOutputSpeech.setText(repromptText);
	            Reprompt reprompt = new Reprompt();
	            reprompt.setOutputSpeech(repromptOutputSpeech);
	            
	            SimpleCard card = new SimpleCard();
	            card.setTitle("Intent Values  ");
	            card.setContent(responseText);

	            return SpeechletResponse.newAskResponse(outputSpeech,reprompt, card);
	           
}
private SpeechletResponse getbody(final Intent intent,final Session session) {
	String responseText1=null;
	Slot nameSlot2=intent.getSlot("body");
	session.setAttribute(BODY_key,nameSlot2.getValue());
  
     		responseText1="You can ask me to send a mail";
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText(responseText1);
            
            String repromptText = "type send mail";
            PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
            repromptOutputSpeech.setText(repromptText);
            Reprompt reprompt = new Reprompt();
            reprompt.setOutputSpeech(repromptOutputSpeech);
            
            SimpleCard card = new SimpleCard();
            card.setTitle("Intent Values  ");
            card.setContent(responseText1);

            return SpeechletResponse.newAskResponse(outputSpeech,reprompt, card);
}

private SpeechletResponse sendmail(final Intent intent,final Session session){
	String username= (String)session.getAttribute(EMAILID_KEY);
	String subject= (String)session.getAttribute(Subject_key);
	String body= (String)session.getAttribute(BODY_key);
	Email.send(username,subject,body);
	String responseText= "Mail sent successfully";
	PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
    outputSpeech.setText(responseText);

    SimpleCard card = new SimpleCard();
    card.setTitle("Intent Values  ");
    card.setContent(responseText);

    return SpeechletResponse.newTellResponse(outputSpeech, card);
    

}
 /* Creates a {@code SpeechletResponse} for the HelpIntent.
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
 *            the re-prompt for if the user doesn't reply or is misunderstood.
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
