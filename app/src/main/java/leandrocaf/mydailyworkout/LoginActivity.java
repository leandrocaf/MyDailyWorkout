package leandrocaf.mydailyworkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends BaseActivity {

    private ParseUser loggedUser;
    private EditText userName;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.userName = (EditText)findViewById(R.id.editTextLogin);
        this.password = (EditText)findViewById(R.id.editTextPassword);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "bgE7ilcmKK05ukAypIxLiQQamsnSeyVlUDl1WOUb", "o13tP61th9ihWgK8mzWJH5hGnOFy9Pzx4oXl5SC2");

        Button btnLogin = (Button)findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        Button btnSingUp = (Button)findViewById(R.id.buttonSignUp);
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               register();
            }
        });

    }

    private void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    };

    private void login() {
        ParseUser.logInInBackground(this.userName.getText().toString(), this.password.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Toast.makeText(getBaseContext(), "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                    loggedUser = user;
                    password.setText("");
                    goToHomeScreen();

                } else {
                    Toast.makeText(getBaseContext(), "Ops!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void goToHomeScreen(){
        Intent intent;

        if(loggedUser.getBoolean("admin")){
            intent = new Intent(this, AdminActivity.class);
//            myIntent.putExtra("key", value); //Optional parameter
        } else {
            intent = new Intent(this, UserActivity.class);
        }

        this.startActivity(intent);
    }
}
