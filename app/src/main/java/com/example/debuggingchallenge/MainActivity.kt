package com.example.debuggingchallenge

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private lateinit var llMain: LinearLayout
    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var password2: EditText
    private lateinit var submitBtn: Button
    private lateinit var activeUsers: TextView

    private lateinit var userNameLength: TextView
    private lateinit var userNameNumber: TextView
    private lateinit var userNameCharacters: TextView
    private lateinit var passwordLength: TextView
    private lateinit var passwordUppercase: TextView
    private lateinit var passwordNumber: TextView
    private lateinit var passwordCharacter: TextView

    private var users = arrayListOf(
        "Freddy",
        "Jason",
        "Ripley",
        "Poncho",
        "Saitama",
        "Archer",
        "Derek",
        "Pamela",
        "Simba",
        "Simon",
        "Retsy",
        "Peter",
        "Daria",
        "Smitty"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llMain = findViewById(R.id.llMain)
        userName = findViewById(R.id.etUsername)
        password = findViewById(R.id.etPassword)
        password2 = findViewById(R.id.etConfirmPassword)
        submitBtn = findViewById(R.id.btSubmit)
        submitBtn.setOnClickListener {
            if(usernameAccepted(userName.text.toString()) && passwordAccepted(password.text.toString())) {
                // 3 My account is created even when I don't enter a second password
                if (password.text.toString() == password2.text.toString()) {
                    Toast.makeText(this, "User created!", Toast.LENGTH_LONG).show()
                    users.add(userName.text.toString().capitalize())
                    displayUsers()
                } else {
                    Toast.makeText(this, "the passwords don't match!", Toast.LENGTH_LONG).show()
                }

            }
        }
        activeUsers = findViewById(R.id.tvActiveUsers)

        userNameLength = findViewById(R.id.tvUsernameLength)
        userNameNumber = findViewById(R.id.tvUsernameNoNumbers)
        userNameCharacters = findViewById(R.id.tvUsernameNoSpacesOrSpecialCharacters)
        passwordLength = findViewById(R.id.tvPasswordLength)
        passwordUppercase = findViewById(R.id.tvPasswordUppercase)
        passwordNumber = findViewById(R.id.tvPasswordNumber)
        passwordCharacter = findViewById(R.id.tvPasswordCharacter)
    }

    private fun usernameAccepted(text: String): Boolean{
        // 1 The app sometimes lets me add an existing user's name     ** if(text !in users){ **
        if(text.capitalize() !in users){
            if(text.length in 5..15){
                userNameLength.setTextColor(Color.GREEN)
                if(!hasNumber(text)){
                    userNameNumber.setTextColor(Color.GREEN)
                    if(!hasSpecial(text) && !text.contains(" ")){
                        userNameCharacters.setTextColor(Color.GREEN)
                        return true
                    }
                    userNameCharacters.setTextColor(Color.RED)
                    //Toast.makeText(this, "The username cannot contain special characters or spaces", Toast.LENGTH_LONG).show()
                }
                userNameNumber.setTextColor(Color.RED)
                //Toast.makeText(this, "The username cannot contain numbers", Toast.LENGTH_LONG).show()
            }
            userNameLength.setTextColor(Color.RED)
            //Toast.makeText(this, "The username must be between 5 and 15 characters long", Toast.LENGTH_LONG).show()
        }
        // 4 I sometimes get two error messages when adding a user      ** inside else **
        else {
            Toast.makeText(this, "The username is already taken", Toast.LENGTH_LONG).show()
        }
        return false
    }

    private fun passwordAccepted(text: String): Boolean{
        if(text.length >= 8){
            passwordLength.setTextColor(Color.GREEN)
            if(hasUpper(text)){
                passwordUppercase.setTextColor(Color.GREEN)
                if(hasNumber(text)){
                    passwordNumber.setTextColor(Color.GREEN)
                    if(hasSpecial(text)){
                        passwordCharacter.setTextColor(Color.GREEN)
                        return true
                    }
                    passwordCharacter.setTextColor(Color.RED)
                    Toast.makeText(this, "The password must contain a special character", Toast.LENGTH_LONG).show()
                }
                passwordNumber.setTextColor(Color.RED)
                Toast.makeText(this, "The password must contain a number", Toast.LENGTH_LONG).show()
            }
            passwordUppercase.setTextColor(Color.RED)
            Toast.makeText(this, "The password must contain an uppercase letter", Toast.LENGTH_LONG).show()
        }
        else {
            passwordLength.setTextColor(Color.RED)
            Toast.makeText(this, "The password must be at least 8 characters long", Toast.LENGTH_LONG).show()
        }
        return false
    }

    private fun hasUpper(text: String): Boolean{
        var letter = 'A'
        while (letter <= 'Z') {
            // 2 Some of my passwords get rejected even when I use a capital letter      ** if(text[0] == letter) **
                for (text in text) {
                    if(text == letter){
                        return true
                    }
                }

            ++letter
        }
        return false
    }

    private fun hasNumber(text: String): Boolean{
        for(i in 0..9){
            if(text.contains(i.toString())){
                return true
            }
        }
        return false
    }

    private fun hasSpecial(text: String): Boolean{
        val specialCharacters = "!@#$%"
        for(special in specialCharacters){
            if(text.contains(special)){
                return true
            }
        }
        return false
    }

    private fun displayUsers(){
        // 5 The entry fields stay on screen when the list of active users is displayed
        // **
        userName.isVisible = false
        password.isVisible = false
        password2.isVisible = false
        submitBtn.isVisible = false
        userNameLength.isVisible = false
        userNameNumber.isVisible = false
        userNameCharacters.isVisible = false
        passwordLength.isVisible = false
        passwordUppercase.isVisible = false
        passwordNumber.isVisible = false
        passwordCharacter.isVisible = false
        // **

        var allUsers = "Active Users:\n\n"
        for(user in users){
            allUsers += user + "\n"
        }
        activeUsers.text = allUsers
        activeUsers.isVisible = true
    }
}