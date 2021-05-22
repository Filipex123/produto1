package ferramenta;

import bd.dbos.LabirintoDBO;
import network.servidor.LabirintoNetworkEntity;

/**
 * Adapter para entidade de banco e rede
 */
public class LabirintoAdapter {

    /**
     * Converte uma entidade de rede em entidade de banco
     * @param networkEntity entidade de rede
     * @return LabirintoDBO entidade de banco
     */
    public static LabirintoDBO toDBO(final LabirintoNetworkEntity networkEntity) {
        return new LabirintoDBO(networkEntity.getNome(),
                networkEntity.getIdentificador(),
                null,
                null,
                networkEntity.getConteudo());
    }

    /**
     * Converte uma entidade de rede em entidade de banco
     * @param labirintoDBO entidade de banco
     * @return LabirintoNetworkEntity entidade de rede
     */
    public static LabirintoNetworkEntity toNetworkEntity(final LabirintoDBO labirintoDBO) {
        return new LabirintoNetworkEntity(labirintoDBO.getNome(),
                labirintoDBO.getIdentificador(),
                labirintoDBO.getDataCriacao(),
                labirintoDBO.getDataEdicao(),
                labirintoDBO.getConteudo());
    }
}
