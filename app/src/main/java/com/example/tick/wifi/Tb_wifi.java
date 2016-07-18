package com.example.tick.wifi;

/**
 * Created by tick on 2016/7/18.
 */
public class Tb_wifi {
   // private int _id;
    private String name;
    private String password;
    public Tb_wifi(){
        super();
    }
    public Tb_wifi(String name,String password){
        super();
        //this._id=id;
        this.name = name;
        this.password = password;
    }
/*

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
