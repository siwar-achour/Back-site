package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChauffeurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chauffeur.class);
        Chauffeur chauffeur1 = new Chauffeur();
        chauffeur1.setId("id1");
        Chauffeur chauffeur2 = new Chauffeur();
        chauffeur2.setId(chauffeur1.getId());
        assertThat(chauffeur1).isEqualTo(chauffeur2);
        chauffeur2.setId("id2");
        assertThat(chauffeur1).isNotEqualTo(chauffeur2);
        chauffeur1.setId(null);
        assertThat(chauffeur1).isNotEqualTo(chauffeur2);
    }
}
