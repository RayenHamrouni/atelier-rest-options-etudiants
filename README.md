# Atelier REST n°1 – Mise en place d'un service web RESTful

**Étudiant :** Rayen Hamrouni  **Classe :** 3A23  **Matière :** Architecture Orientée Services (SOA) – Esprit

## 1. Objectif
Exposer sous forme d'API REST (JAX-RS / Jersey 2.27, Tomcat 9.0.75, Java 17) la gestion des **options** et des **étudiants** à partir des classes métier fournies (données en mémoire).

URL de base : `http://localhost:8080/Gestion_Options_Etudiants/rest/options`

## 2. Travail réalisé
| Fichier | Rôle |
|---|---|
| `utilities/RestActivator.java` | Active JAX-RS avec `@ApplicationPath("rest")` |
| `ressources/OptionResource.java` | Ressource `/options` (A.1 à A.6) |
| `ressources/EtudiantResource.java` | Ressource `/etudiants` (B.1 à B.6) |
| `utilities/JacksonConfig.java` | Garde le champ `option` dans le JSON malgré `@XmlTransient` |

### Corrections apportées au projet fourni
1. **Données réinitialisées à chaque requête** : les constructeurs de `OptionBusiness` et `EtudiantBusiness` recréaient les listes statiques à chaque `new`. Ajout d'un test (`if (liste != null) return;`) pour n'initialiser qu'une seule fois.
2. **Champ `option` absent du JSON** : `Etudiant.getOption()` est `@XmlTransient` (utile pour le XML de B.6) et Jackson respectait cette annotation. `JacksonConfig` fournit un `ObjectMapper` qui l'ignore : l'option reste dans le JSON, et absente du XML.

## 3. Tests (captures d'écran)

### A. Ressource Option
**A.1 – POST /options** → 200
![A1](screenshots/01_post_options.png)

**A.2 – GET /options** → 200
![A2](screenshots/02_get_options.png)

**A.3 – GET /options?domaine=Mathématiques** → 200
![A3](screenshots/03_get_options_domaine.png)

**A.4 – DELETE /options/2** → 204
![A4](screenshots/04_delete_option.png)

**A.5 – PUT /options/1** → 200
![A5](screenshots/05_put_option.png)

**A.6 – GET /options/1** → 200
![A6](screenshots/06_get_option_1.png)

### B. Ressource Etudiant
**B.1 – POST /etudiants** → 200
![B1](screenshots/07_post_etudiant.png)

**B.2 – GET /etudiants** → 200
![B2](screenshots/08_get_etudiants.png)

**B.3 – GET /etudiants/I003** → 200
![B3](screenshots/09_get_etudiant.png)

**B.4 – DELETE /etudiants/I003** → 204
![B4](screenshots/10_delete_etudiant.png)

**B.5 – PUT /etudiants/I001** → 200
![B5](screenshots/11_put_etudiant.png)

**B.6 – GET /etudiants/option?codeOption=1** → 200 (XML)
![B6](screenshots/12_get_etudiants_option_xml.png)

### Cas d'erreur (404)
**GET /options/99** → 404
![404](screenshots/13_get_option_404.png)

## 4. Lancer le projet
1. Ouvrir le projet dans IntelliJ (Maven) et vérifier la compilation.
2. Configurer Tomcat 9.0.75 (*Run → Edit Configurations → Tomcat Server → Local*).
3. Déployer l'artifact `Gestion_Options_Etudiants:war exploded`, puis lancer.
