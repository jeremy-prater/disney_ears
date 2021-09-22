#include <led_strip.h>

#define CONFIG_LED_STRIP_LEN 96

void leds_init();
void led_task(void *pvParameters);
void leds_update();
void leds_random_fill();
void leds_solid_fill();
void leds_zero_fill();

extern rgb_t led_colors[CONFIG_LED_STRIP_LEN];
