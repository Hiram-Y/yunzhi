/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Dawson
 */
@ManagedBean(name = "indexController")
@ViewScoped
public class IndexController implements Serializable {

    List<String> vedios = new ArrayList<>();

    /**
     * Creates a new instance of IndexController
     */
    public IndexController() {
        vedios.add("http://10.206.11.101:8888/live/livestream.m3u8");
    }

    public List<String> getVedios() {
        return vedios;
    }

    public void setVedios(List<String> vedios) {
        this.vedios = vedios;
    }

}
