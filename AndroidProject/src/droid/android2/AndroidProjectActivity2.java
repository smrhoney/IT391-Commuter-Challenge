package droid.android2;

import android.app.Activity;
import android.content.Intent;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.android.*;
import com.facebook.android.Facebook.*;

public class AndroidProjectActivity2 extends Activity {
	Facebook facebook = new Facebook("232078466852757");
	 
    String FILENAME = "AndroidSSO_data";
    private SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabtracking);

        
        final Button facebookButton = (Button) findViewById(R.id.button1);
        facebookButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doFacebookStuff();
            }
        });
       
       /* final Button googleButton = (Button) findViewByID(R.id.button2);
        googleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doGoogleStuff();
            }
        });*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }
    
    
    public void doGoogleStuff()
    {
    	String authorizationUrl = new GoogleAuthorizationRequestUrl(OAuth2ClientCredentials.CLIENT_ID, OAuth2ClientCredentials.OAUTH_CALLBACK_URL, OAuth2ClientCredentials.SCOPE).build();
    }
    
    public void doFacebookStuff()
    {
    	
    	/*
         * Get existing access_token if any
         */
        mPrefs = getPreferences(MODE_PRIVATE);
        String access_token = mPrefs.getString("access_token", null);
        long expires = mPrefs.getLong("access_expires", 0);
        if(access_token != null) {
            facebook.setAccessToken(access_token);
        }
        if(expires != 0) {
            facebook.setAccessExpires(expires);
        }
        
        /*
         * Only call authorize if the access_token has expired.
         */
        if(!facebook.isSessionValid()) {

            facebook.authorize(this, new String[] {}, new DialogListener() {
                public void onComplete(Bundle values) {
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putString("access_token", facebook.getAccessToken());
                    editor.putLong("access_expires", facebook.getAccessExpires());
                    editor.commit();
                }
    
                public void onFacebookError(FacebookError error) {}
    
                public void onError(DialogError e) {}
    
                public void onCancel() {}
            });
        }
        final EditText text = (EditText) findViewById(R.id.editText1);
        
        text.setText(access_token);
    }
    
    
}