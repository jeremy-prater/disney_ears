# The following lines of boilerplate have to be in your project's CMakeLists
# in this exact order for cmake to work correctly
cmake_minimum_required(VERSION 3.5)
set(ESP_LIB_DIR ${CMAKE_SOURCE_DIR}/../esp-idf-lib/components)
message(${ESP_LIB_DIR})
set(EXTRA_COMPONENT_DIRS ${ESP_LIB_DIR})
include($ENV{IDF_PATH}/tools/cmake/project.cmake)
project(disney_ears_controller)
