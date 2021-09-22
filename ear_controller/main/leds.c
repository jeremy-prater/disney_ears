#include <stdio.h>
#include <freertos/FreeRTOS.h>
#include <freertos/task.h>
#include <esp_system.h>
#include <led_strip.h>
#include "leds.h"

#define LED_TYPE LED_STRIP_WS2812
#define LED_GPIO 5

#define MAX_TEST 4

rgb_t led_colors[CONFIG_LED_STRIP_LEN];

static led_strip_t strip = {
    .type = LED_TYPE,
    .length = CONFIG_LED_STRIP_LEN,
    .gpio = LED_GPIO,
    .buf = NULL,
#ifdef LED_STRIP_BRIGHTNESS
    .brightness = 255,
#endif
};

void leds_init()
{

    ESP_ERROR_CHECK(led_strip_init(&strip));
}

void led_task(void *pvParameters)
{
    while (1)
    {
        leds_update();

        vTaskDelay(pdMS_TO_TICKS(250));
    }
}

void leds_update()
{
    led_strip_set_pixels(&strip, 0, CONFIG_LED_STRIP_LEN, led_colors);
    led_strip_wait(&strip, portMAX_DELAY);
    ESP_ERROR_CHECK(led_strip_flush(&strip));
}

void leds_zero_fill()
{
    memset(led_colors, 0, sizeof(rgb_t) * CONFIG_LED_STRIP_LEN);
}

void leds_random_fill()
{
    for (uint8_t index = 0; index < CONFIG_LED_STRIP_LEN; index++)
    {
        rgb_t value = {
            .r = (esp_random() % MAX_TEST),
            .g = (esp_random() % MAX_TEST),
            .b = (esp_random() % MAX_TEST),
        };
        led_colors[index] = value;
    }
}

void leds_solid_fill()
{
    for (uint8_t index = 0; index < CONFIG_LED_STRIP_LEN; index++)
    {
        rgb_t value = {
            .r = (index + 0) % MAX_TEST,
            .g = (index + 1) % MAX_TEST,
            .b = (index + 2) % MAX_TEST,
        };
        led_colors[index] = value;
    }
}