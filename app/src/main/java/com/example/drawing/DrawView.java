package com.example.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class DrawView extends View {
    private Paint paint;
    private Paint strokePaint;

    public DrawView(Context context) {
        super(context);
        paint = new Paint();
        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStrokeWidth(5f); // Толщина контура
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Устанавливаем светло-голубой цвет фона
        paint.setColor(Color.rgb(173, 216, 230));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        // ======== Рисуем радугу ========
        int[] rainbowColors = {
                Color.RED, Color.parseColor("#FF7F00"), Color.YELLOW,
                Color.GREEN, Color.BLUE, Color.parseColor("#4B0082"), Color.MAGENTA, Color.rgb(173, 216, 230)
        };

        float rainbowCenterX = getWidth() / 2; // Центр радуги
        float rainbowCenterY = getHeight() * 0.65f; // Расположение радуги выше дома
        float rainbowRadius = getWidth() / 2 + 200; // Радиус радуги на весь экран с небольшим выходом

        for (int i = 0; i < rainbowColors.length; i++) {
            paint.setColor(rainbowColors[i]);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawArc(rainbowCenterX - rainbowRadius, rainbowCenterY - rainbowRadius,
                    rainbowCenterX + rainbowRadius, rainbowCenterY + rainbowRadius,
                    180, 180, true, paint);
            rainbowRadius -= 20; // Уменьшаем радиус для следующего цвета
        }

        // ======== Рисуем солнце ========
        float sunCenterX = getWidth() / 2; // Центр солнца
        float sunCenterY = getHeight() * 0.1f; // Расположение солнца выше
        float sunRadius = 50; // Радиус солнца

        paint.setColor(Color.YELLOW); // Цвет солнца
        canvas.drawCircle(sunCenterX, sunCenterY, sunRadius, paint);

        // Рисуем лучи
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(5);
        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians(i * 30); // Угол для лучей
            float startX = sunCenterX + (float) (sunRadius * Math.cos(angle));
            float startY = sunCenterY + (float) (sunRadius * Math.sin(angle));
            float endX = sunCenterX + (float) ((sunRadius + 100) * Math.cos(angle)); // Увеличиваем длину лучей
            float endY = sunCenterY + (float) ((sunRadius + 100) * Math.sin(angle));
            canvas.drawLine(startX, startY, endX, endY, paint); // Рисуем лучи
        }

        // Рисуем прямоугольник (земля) внизу экрана
        paint.setColor(Color.rgb(0, 100, 0)); // Темно-зеленый цвет
        float left = 0;
        float top = getHeight() * 0.75f;  // Начинаем рисовать на 3/4 высоты экрана
        float right = getWidth();
        float bottom = getHeight();
        canvas.drawRect(left, top, right, bottom, paint);

        // ======== Рисуем дом с контурами ========
        paint.setColor(Color.rgb(255, 204, 0)); // Жёлтый цвет
        float houseLeft = getWidth() * 0.2f; // ширина дома
        float houseTop = getHeight() * 0.55f; // высота дома
        float houseRight = getWidth() * 0.8f; // Широкий дом
        float houseBottom = getHeight() * 0.75f; // Нижняя часть дома остаётся на том же уровне
        canvas.drawRect(houseLeft, houseTop, houseRight, houseBottom, paint);
        // Контур для стен
        canvas.drawRect(houseLeft, houseTop, houseRight, houseBottom, strokePaint);

        // 2. Красная крыша (треугольник)
        paint.setColor(Color.rgb(255, 0, 0)); // Красный цвет
        Path roof = new Path();
        roof.moveTo(houseLeft, houseTop); // Левый верхний угол
        roof.lineTo(houseRight, houseTop); // Правый верхний угол
        roof.lineTo((houseLeft + houseRight) / 2, houseTop - (houseRight - houseLeft) / 4); // Верх треугольника (середина)
        roof.close();
        canvas.drawPath(roof, paint);
        // Контур для крыши
        canvas.drawPath(roof, strokePaint);

        // 3. Коричневая дверь (прямоугольник)
        paint.setColor(Color.rgb(139, 69, 19)); // Коричневый цвет
        float doorLeft = (houseLeft + houseRight) / 2 - getWidth() * 0.06f; // Ширина двери немного больше
        float doorTop = houseBottom - (houseBottom - houseTop) * 0.6f; // Дверь чуть выше, чем в прошлый раз
        float doorRight = (houseLeft + houseRight) / 2 + getWidth() * 0.06f; // Новая ширина
        float doorBottom = houseBottom;
        canvas.drawRect(doorLeft, doorTop, doorRight, doorBottom, paint);
        // Контур для двери
        canvas.drawRect(doorLeft, doorTop, doorRight, doorBottom, strokePaint);

        // Рисуем вертикальные полоски на двери
        paint.setColor(Color.rgb(0, 0, 0)); // Цвет полосок (черный)
        int numStripes = 6; // Количество полосок
        float totalWidth = doorRight - doorLeft; // Общая ширина двери
        float stripeWidth = 3; // Установим ширину полоски в 3 пикселя
        float spacing = (totalWidth - (numStripes * stripeWidth)) / (numStripes + 1); // Расстояние между полосками

        // Рисуем полоски
        for (int i = 0; i < numStripes; i++) {
            float x = doorLeft + spacing + i * (stripeWidth + spacing); // Расчет позиции каждой полоски
            canvas.drawRect(x, doorTop, x + stripeWidth, doorBottom, paint); // Рисуем вертикальные полоски
        }

        // 4. Окна (белые квадраты)
        paint.setColor(Color.WHITE); // Белый цвет
        float windowWidth = (houseRight - houseLeft) * 0.15f; // Ширина окон
        float windowHeight = (houseBottom - houseTop) * 0.35f; // Высота окон

        // Левое окно рядом с дверью
        float windowLeft1 = houseLeft + (houseRight - houseLeft) * 0.1f;
        float windowTop1 = houseBottom - windowHeight - 150; // Окно выше двери на 150 пикселей
        canvas.drawRect(windowLeft1, windowTop1, windowLeft1 + windowWidth, windowTop1 + windowHeight, paint);
        // Контур для левого окна
        canvas.drawRect(windowLeft1, windowTop1, windowLeft1 + windowWidth, windowTop1 + windowHeight, strokePaint);

        // Рисуем решетку на левом окне
        paint.setColor(Color.BLACK); // Цвет решетки (черный)
        float gridSpacing = windowWidth / 4; // Расстояние между полосками
        for (int i = 1; i < 4; i++) {
            // Вертикальные линии
            float x = windowLeft1 + i * gridSpacing;
            canvas.drawLine(x, windowTop1, x, windowTop1 + windowHeight, paint);
            // Горизонтальные линии
            float y = windowTop1 + i * (windowHeight / 3);
            canvas.drawLine(windowLeft1, y, windowLeft1 + windowWidth, y, paint);
        }
        paint.setColor(Color.WHITE); // Белый цвет
        float windowLeft2 = houseRight - (houseRight - houseLeft) * 0.1f - windowWidth; // Окно справа от двери
        float windowTop2 = windowTop1; // Окно на одном уровне с первым
        canvas.drawRect(windowLeft2, windowTop2, windowLeft2 + windowWidth, windowTop2 + windowHeight, paint);
        // Контур для второго окна
        canvas.drawRect(windowLeft2, windowTop2, windowLeft2 + windowWidth, windowTop2 + windowHeight, strokePaint);
        // Рисуем решетку на правом окне
        paint.setColor(Color.BLACK); // Цвет решетки
        int numVerticalLines = 3; // Количество вертикальных линий
        int numHorizontalLines = 2; // Количество горизонтальных линий
        float gridSpacing1 = windowWidth / (numVerticalLines + 1); // Ширина между вертикальными линиями

        // Вертикальные линии
        for (int i = 1; i <= numVerticalLines; i++) {
            float x = windowLeft2 + i * gridSpacing1; // Измените здесь на gridSpacing1
            canvas.drawLine(x, windowTop2, x, windowTop2 + windowHeight, paint); // Рисуем вертикальные линии
        }

        // Горизонтальные линии
        for (int i = 1; i <= numHorizontalLines; i++) {
            float y = windowTop2 + i * (windowHeight / (numHorizontalLines + 1)); // Позиция Y для каждой горизонтальной линии
            canvas.drawLine(windowLeft2, y, windowLeft2 + windowWidth, y, paint); // Рисуем горизонтальные линии
        }

    }
}
