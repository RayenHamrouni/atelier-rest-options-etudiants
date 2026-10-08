package utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Etudiant.getOption() est marque @XmlTransient (pour le XML de l'exercice B.6).
 * Par defaut Jackson respecte cette annotation JAXB et IGNORERAIT "option" en JSON.
 * Ce ObjectMapper "pur Jackson" ignore les annotations JAXB : option reste presente en JSON.
 */
@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
