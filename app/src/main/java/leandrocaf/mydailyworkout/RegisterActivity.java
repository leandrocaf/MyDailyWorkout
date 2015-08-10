package leandrocaf.mydailyworkout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends BaseActivity {

    private EditText name;
    private EditText age;
    private RadioButton genre;
    private EditText weight;
    private EditText email;
    private EditText userLogin;
    private EditText password;
    private EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loadFields();

        Button btnCadastrar = (Button)findViewById(R.id.buttonCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singUp();
            }
        });


    }

    private void loadFields() {
        this.name = (EditText)findViewById(R.id.editTextName);
        this.email = (EditText)findViewById(R.id.editTextEmail);
        this.age =  (EditText)findViewById(R.id.editTextAge);
        this.genre = (RadioButton)findViewById(R.id.radioButtonMan);
        this.weight = (EditText)findViewById(R.id.editTextWeight);
        this.userLogin = (EditText)findViewById(R.id.editTextLogin);
        this.password = (EditText)findViewById(R.id.editTextPassword);
        this.confirmPassword = (EditText)findViewById(R.id.editTextConfirmPassword);
    }

    private void singUp() {
        ParseUser user = new ParseUser();
        user.setUsername(this.userLogin.getText().toString());
        user.setPassword(this.password.getText().toString());
        user.setEmail(this.email.getText().toString());
        user.put("genre", this.genre.isChecked() ? "M" : "F");

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getBaseContext(), "Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                    userLogin.setText("");
                    password.setText("");
                } else {
                    Toast.makeText(getBaseContext(), "Erro ao cadastrar! " +e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
