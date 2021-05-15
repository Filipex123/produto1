package network.servidor;

import bd.dbos.LabirintoDBO;
import network.entidade.Comunicado;


import java.util.ArrayList;

public class LabirintoNetworkEntity extends Comunicado {

    private ArrayList<LabirintoDBO> labirintos;

    public LabirintoNetworkEntity() {
        this.labirintos = new ArrayList<>();
    }

    public void addLabirinto(LabirintoDBO novo) {
        labirintos.add(novo);
    }

    public double getQtd() {
        return labirintos.size();
    }

    public LabirintoDBO getLabirinto(int i) {
        return labirintos.get(i);
    }

}