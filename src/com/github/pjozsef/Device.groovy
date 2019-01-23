package com.github.pjozsef

import groovy.transform.TupleConstructor

@TupleConstructor()
class Device implements Serializable {
    def name
    def status
    def usb
    def product
    def model
    def device
    def transportId

    @Override
    String toString(){
        return "Device($name, $status, $usb, $product, $model, $device, $transportId)"
    }
}
