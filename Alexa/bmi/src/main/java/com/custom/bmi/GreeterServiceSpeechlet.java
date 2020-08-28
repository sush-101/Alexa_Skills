package com.custom.bmi;
import com.amazon.speech.slu.Slot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.speechlet.Session;
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
private static final String NUMA_KEY= "numa";
private static final String NUMB_KEY= "numb";


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
           "Welcome to Alexa BMI Calculator Skill. To calculate bmi"
                  + " provide weight in kilograms";
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

    if ("weightintent".equals(intentName)) {
        return getHelloWorldIntent(intent,session);
    } else if("heightintent".equals(intentName)) {
    	return getheight(intent,session);
    } else if("tellbmi".equals(intentName)) {
    	return tellbmi(intent,session);
    }else if ("AMAZON.HelpIntent".equals(intentName)) {
        return getHelp();
    } else if ("AMAZON.StopIntent".equals(intentName)) {
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText("Have a nice day.Goodbye");

        return SpeechletResponse.newTellResponse(outputSpeech);
    } else if ("AMAZON.CancelIntent".equals(intentName)) {
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText("Have a nice day!");

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
	String responseText = null;
	     Slot nameSlot=intent.getSlot("numa");
	     session.setAttribute(NUMA_KEY,nameSlot.getValue());
   
       responseText="Provide the height in centimetres";
       PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
       outputSpeech.setText(responseText);
       PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
       repromptOutputSpeech.setText("hello");
       Reprompt reprompt = new Reprompt();
       reprompt.setOutputSpeech(repromptOutputSpeech);
       SimpleCard card = new SimpleCard();
       card.setTitle("Intent Values");
       card.setContent(responseText);

       return SpeechletResponse.newAskResponse(outputSpeech,reprompt, card);
      
}

private SpeechletResponse getheight(final Intent intent,final Session session) {
	String responseText = null;
    Slot nameSlot=intent.getSlot("numb");
    session.setAttribute(NUMB_KEY,nameSlot.getValue());
    responseText=" You can ask me the bmi of provided details.";
   PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
   outputSpeech.setText(responseText);

   PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
   repromptOutputSpeech.setText("xz");
   Reprompt reprompt = new Reprompt();
   reprompt.setOutputSpeech(repromptOutputSpeech);
   SimpleCard card = new SimpleCard();
   card.setTitle("Intent Values");
   card.setContent(responseText);

   return SpeechletResponse.newAskResponse(outputSpeech, reprompt,card);
}
private SpeechletResponse tellbmi(final Intent intent,final Session session) {
	String responseText=null;
	String c= (String)session.getAttribute(NUMA_KEY);
	String d= (String)session.getAttribute(NUMB_KEY);
	Double x= Double.parseDouble(c);
	Double y= Double.parseDouble(d);
	Double bmi=(x*10000/(y*y));
	
	if(bmi<18.5) {
		 responseText= "BMI is " +bmi +  " and the person is of underweight " ;
	}
	if(bmi>=18.5 && bmi < 24.9) {
		 responseText= "BMI is " +bmi+  " and the person is of  noraml weight" ;
	}
	if(bmi>=24.9 && bmi < 29.9) {
		 responseText= "BMI is " +bmi+  " and the person is of  overweight" ;
	}
	if(bmi>30) {
		responseText= "BMI is " +bmi+  " and the person is obese" ;
	}
	 PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
	   outputSpeech.setText(responseText);

	   PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
	   repromptOutputSpeech.setText("xz");
	   Reprompt reprompt = new Reprompt();
	   reprompt.setOutputSpeech(repromptOutputSpeech);
	   SimpleCard card = new SimpleCard();
	   card.setTitle("Intent Values");
	   card.setContent(responseText);

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
