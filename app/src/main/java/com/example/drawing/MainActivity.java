package com.example.drawing;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Создаем экземпляр нашего кастомного представления и устанавливаем его как главное содержимое экрана
        DrawView drawView = new DrawView(this);
        setContentView(drawView);
    }
}