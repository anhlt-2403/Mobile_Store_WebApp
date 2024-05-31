/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Devices;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tuấn Anh
 */
public class CartDTO {
    private Map<String, MobileDTO> cart;
    
    public CartDTO(){
    }

    public CartDTO(Map<String, MobileDTO> cart) {
        this.cart = cart;
    }

    public Map<String, MobileDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, MobileDTO> cart) {
        this.cart = cart;
    }
    
    public void add(MobileDTO mobile){
        if(this.cart == null){
            this.cart = new HashMap<String, MobileDTO>();
        }
        if(this.cart.containsKey(mobile.getMobileID())){
            int currentQuantity = this.cart.get(mobile.getMobileID()).getQuantity();
            mobile.setQuantity(currentQuantity + mobile.getQuantity());
        }
        this.cart.put(mobile.getMobileID(), mobile);
    }
    
    public void remove(String mobileID) {
        if (this.cart == null) {
            return;
        }
        if (this.cart.containsKey(mobileID)) {
            this.cart.remove(mobileID);
        }
    }
}
