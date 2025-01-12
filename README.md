# **Outil de Gestion des Albums DICOM**  
Ce projet consiste en la création d'un outil permettant de faciliter la gestion des images DICOM, notamment la création d'albums à partir de sous-ensembles d'images DICOM. L'objectif principal est de simplifier les workflows des chercheurs en automatisant la gestion des données médicales et en permettant l'extraction et la recherche de métadonnées DICOM.

## **Qu'est-ce que DICOM ?**
DICOM (Digital Imaging and Communications in Medicine) est un standard utilisé pour l'échange, le stockage et la transmission d'images médicales ainsi que des informations associées. Il est largement utilisé dans les hôpitaux, cliniques et instituts de recherche pour le stockage et l'analyse d'images médicales telles que les radiographies, les tomographies, les IRM, les échographies et bien plus encore.

### **Caractéristiques du format DICOM :**
- **Images médicales** : DICOM est utilisé pour représenter des images issues d'équipements médicaux comme les scanners, les appareils IRM, les échographes, etc.
- **Métadonnées** : En plus des images, les fichiers DICOM contiennent des métadonnées qui décrivent le patient, les paramètres de l'examen, le type d'image, etc.
- **Interopérabilité** : DICOM permet à des systèmes informatiques différents, provenant de différents fabricants, de communiquer et d'échanger des données de manière uniforme.

## **Qui utilise DICOM ?**
- **Médecins et Radiologues** : Les professionnels de la santé utilisent les images DICOM pour diagnostiquer et analyser les conditions médicales des patients.
- **Chercheurs en Médecine et en Imagerie Médicale** : Les chercheurs qui travaillent sur l'analyse des images médicales utilisent DICOM pour traiter et analyser de grandes quantités de données d'imagerie.
- **Développeurs et Ingénieurs en Informatique Médicale** : Les développeurs créent des outils et des logiciels permettant de gérer, stocker et analyser les données DICOM.
- **Hôpitaux et Cliniques** : Ces établissements utilisent DICOM pour stocker les images médicales dans leurs systèmes d'archivage et de communication d'images (PACS).

## **Description du projet**
Cet outil est conçu pour permettre aux chercheurs et professionnels de la santé de créer, gérer et manipuler des albums à partir d'images DICOM stockées localement. Les fonctionnalités principales incluent :
- **Requête de métadonnées** : Permet aux utilisateurs de filtrer les images DICOM en fonction de certaines métadonnées (par exemple, le nom du patient, la date de l'examen, etc.).
- **Création et gestion d'albums** : Les utilisateurs peuvent organiser des images DICOM en albums, ajouter ou supprimer des images, et gérer les informations associées.
- **Interface graphique** : L'outil offre une interface graphique conviviale qui facilite l'interaction avec les données DICOM.

## **Technologies utilisées**
- **JavaFX** : Pour l'interface graphique (GUI) permettant une expérience utilisateur fluide.

## **Fonctionnalités principales**
- **Recherche et filtrage des images DICOM** : Les utilisateurs peuvent effectuer des recherches en fonction des métadonnées comme le nom du patient, le type d'examen, etc.
- **Création d'albums** : Les images sélectionnées peuvent être ajoutées à un album.
- **Gestion des albums** : Les albums peuvent être créés, modifiés, et supprimés. Les utilisateurs peuvent ajouter ou supprimer des images des albums.
- **Gestion des métadonnées** : Les métadonnées des images DICOM peuvent être affichées et utilisées pour des requêtes.

## **Prérequis**
- **Java 8 ou supérieur**  
- **Bibliothèques nécessaires** :
  - JavaFX  
 


## **Contribution**
Les contributions sont les bienvenues ! Si vous avez des suggestions ou des améliorations à proposer, n'hésitez pas à ouvrir une **pull request**.
