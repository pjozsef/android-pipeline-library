package com.github.pjozsef

import groovy.transform.TupleConstructor

@TupleConstructor()
class Device {
    def name
    def status
    def usb
    def product
    def model
    def device

    @Override
    String toString(){
        return "$name, $status, $usb, $product, $model, $device"
    }
}