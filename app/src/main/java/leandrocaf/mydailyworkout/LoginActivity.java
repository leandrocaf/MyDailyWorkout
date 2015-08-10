package leandrocaf.mydailyworkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends BaseActivity {

    private ParseUser loggedUser;
    private EditText userName;
    private EditText password;
    private ProgressBar spinner;
    private RelativeLayout layoutLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.userName = (EditText)findViewById(R.id.editTextLogin);
        this.password = (EditText)findViewById(R.id.editTextPassword);

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

        layoutLogin = (RelativeLayout)findViewById(R.id.layoutLogin);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

    }

    private void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    };

    private void login() {
        showLoading(true);
        ParseUser.logInInBackground(this.userName.getText().toString(), this.password.getText().toString(), new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    showLoading(false);
                    Toast.makeText(getBaseContext(), "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                    loggedUser = user;
                    password.setText("");
                    goToHomeScreen();

                } else {
                    showLoading(false);
                    Toast.makeText(getBaseContext(), "Ops!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void goToHomeScreen(){
        Intent intent;

        if(loggedUser.getBoolean("admin")){
            intent = new Intent(this, AdminActivity.class);
        } else {
            intent = new Intent(this, UserActivity.class);
        }

        this.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void showLoading(Boolean show){
        if(show) {
            spinner.setVisibility(View.VISIBLE);
            layoutLogin.setVisibility(View.GONE);
        } else {
            spinner.setVisibility(View.GONE);
            layoutLogin.setVisibility(View.VISIBLE);
        }
    }
}
