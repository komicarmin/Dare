package com.aagjm.dare;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by slamo on 27. 01. 2017.
 */
//Volley bo s tem razredom naredil request na php skripto dobili jo bomo pa v obliki Stringa(json)
public class RegisterRequest extends StringRequest {
    //najprej mu povemo URL
    private final static String url = "https://unswerving-register.000webhostapp.com/scripts/insert.php";
    private Map<String,String> params;

    //konstruktor sprejema tiste stvari katere so pomembne za registracijo
    //potrebno je še dodati MAC naslov naprave(trenutno je hard inconded
    RegisterRequest(String ime, String priimek, String geslo, String mac,int finished, Response.Listener<String> listener){
        //da bi lahko naredili request moramo poklicati konstruktor od StringRequest(tukaj pride Volley prav)
        //super(metoda_pošiljanja,url_naslov,listener,error listener)
        //listener potrebuje zato, da ko Volley zaključi z zahtevo aktivira listenerja
        super(Method.POST,url,listener,null);
        params = new HashMap<>();
        params.put("Ime",ime);
        params.put("Priimek",priimek);
        params.put("Geslo",geslo);
        params.put("MAC",mac);
        params.put("finished_count",finished +"");

    }
    //ko bo zahteva izvršena bo Volley poklical metodo getParams() da dobi podatke
    //metodo moramo povoziti ter ji vnesti podatke
    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
