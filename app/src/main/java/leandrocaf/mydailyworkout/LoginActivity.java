package leandrocaf.mydailyworkout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

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
                singUp();
            }
        });

    }



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


    private void singUp() {
        ParseUser user = new ParseUser();
        user.setUsername(this.userName.getText().toString());
        user.setPassword(this.password.getText().toString());
        user.setEmail("email@example.com");
//
//        user.put("phone", "650-253-0000");

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getBaseContext(), "Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                    userName.setText("");
                    password.setText("");
                } else {
                    Toast.makeText(getBaseContext(), "Erro ao cadastrar! " +e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
