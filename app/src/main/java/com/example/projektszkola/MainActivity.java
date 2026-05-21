package com.example.projektszkola;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // ===== TIMER =====
    TextView textViewTimer;
    Handler handler = new Handler(Looper.getMainLooper());
    int sekundy = 0;
    boolean graTrwa = true;
    Runnable timerRunnable;

    // ===== QUIZ =====
    TextView textViewPytanie;
    Button btnTak, btnNie;

    ArrayList<Pytanie> listaPytan = new ArrayList<>();
    int indeks = 0;
    int punkty = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewPytanie = findViewById(R.id.textViewPytanie);
        btnTak = findViewById(R.id.btnTak);
        btnNie = findViewById(R.id.btnNie);

        // ===== PYTANIA (10) =====
        listaPytan.add(new Pytanie("2 + 2 = 4?", true));
        listaPytan.add(new Pytanie("Ziemia jest płaska?", false));
        listaPytan.add(new Pytanie("Leonardo Da Vinci namalował obraz Ostatnia Wieczerza?", true));
        listaPytan.add(new Pytanie("10 > 20?", false));
        listaPytan.add(new Pytanie("Polska jest w Europie?", true));
        listaPytan.add(new Pytanie("5 + 5 * 5 = 20?", false));
        listaPytan.add(new Pytanie("Android to system mobilny?", true));
        listaPytan.add(new Pytanie("Czy stolicą Chin jest Szanghaj?", true));
        listaPytan.add(new Pytanie("Czy stolicą Włoch jest Rzym?", true));
        listaPytan.add(new Pytanie("Czy w Polsce jest 15 województw?", false));

        pokazPytanie();
        startTimer();

        btnTak.setOnClickListener(v -> sprawdz(true));
        btnNie.setOnClickListener(v -> sprawdz(false));
    }

    // ===== TIMER =====
    void startTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (graTrwa) {
                    sekundy++;
                    textViewTimer.setText(formatCzas(sekundy));
                    handler.postDelayed(this, 1000);
                }
            }
        };

        handler.post(timerRunnable);
    }

    String formatCzas(int sek) {
        int minuty = (sek / 60);
        int sekundy = (sek % 60);
        return String.format("%02d:%02d", minuty, sekundy);
    }

    // ===== QUIZ =====
    void pokazPytanie() {
        textViewPytanie.setText(listaPytan.get(indeks).tresc);
    }

    void sprawdz(boolean odpowiedzUzytkownika) {

        if (indeks >= listaPytan.size()) return;

        Pytanie p = listaPytan.get(indeks);

        if (p.odpowiedz == odpowiedzUzytkownika) {
            punkty++;
            Toast.makeText(this, "Dobra odpowiedź", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Zła odpowiedź", Toast.LENGTH_SHORT).show();
        }

        indeks++;

        if (indeks < listaPytan.size()) {
            pokazPytanie();
        } else {
            koniecQuizu();
        }
    }

    void koniecQuizu() {
        graTrwa = false;

        textViewPytanie.setText(
                "KONIEC QUIZU\n\n" +
                        "Wynik: " + punkty + "/" + listaPytan.size() +
                        "\nCzas: " + formatCzas(sekundy)
        );

        btnTak.setEnabled(false);
        btnNie.setEnabled(false);
    }
}