# Compte rendu de L'activité Pratique N° 1: "Inversion de contrôle et Injection des dépendances"

## Sommaire
- [1. Créer l'interface IDao avec une méthode getData](#cr%C3%A9ation-de-linterface-idao)
- [2. Créer une implémentation de cette interface](#cr%C3%A9ation-dune-impl%C3%A9mentation-de-cette-interface)
- [3. Créer l'interface IMetier](#cr%C3%A9ation-de-linterface-imetier)
- [4. Créer une implémentation de cette interface en utilisant le couplage faible](#cr%C3%A9ation-dune-impl%C3%A9mentation-de-cette-interface-en-utilisant-le-couplage-faible)
- [5. Faire l'injection des dépendances](#linjection-des-d%C3%A9pendances) 
  - [a. Par instanciation statique](#par-instanciation-statique)
  - [b. Par instanciation dynamique](#par-instanciation-dynamique)
  - [c.
  En utilisant le Framework Spring](#en-utilisant-le-framework-spring)
    - [Version XML](#version-xml)
    - [Version annotations](#version-annotations)
    
    
    ## Création de l'interface IDao
    ```JAVA
  
        package dao;

        public interface IDao {
            double getData();
        }
    ```
    ## Création d'une implémentation de cette interface
    ```JAVA
    package dao;
    public class DaoImpl implements IDao{

    @Override
    public double getData() {
        /*
        Se connecter a la BD pour recuperer la temperature
        */
        System.out.println("Version 1");
        double temp=Math.random()*40;
        return temp;
                 }
        }
    ```
    ##  Création de l'interface IMetier
    
     ```JAVA
     package metier;
     public interface IMetier {
             double calcul();
     }
    ```
    ##  Création d'une implémentation de cette interface en utilisant le couplage faible
     ```JAVA
        package metier;

        import dao.IDao;

        public class MetierImpl implements IMetier{
            private IDao dao;

            @Override
            public double calcul() {
                double tmp=dao.getData();
                return tmp*540/Math.cos(tmp*Math.PI);
            }
            public void setDao(IDao dao) {
                this.dao = dao;
            }
        }

    ```
    ## l'injection des dépendances
    
     ## Par instanciation statique
        Code source
     ```JAVA
        package pres;

        import dao.DaoImpl;
        import ext.DaoImpl2;
        import metier.MetierImpl;
        public class Presentation {
            public static void main(String[] args) {
                DaoImpl2 dao=new DaoImpl2();
                MetierImpl metier=new MetierImpl();
                metier.setDao(dao);
                System.out.println("Resultat = "+ metier.calcul());
            }
        }
    ```
    
                -Execution
            
                
      ## Par instanciation dynamique
        - On crée un fichier configurable
        
     ```
        ext.DaoImpl2
        metier.MetierImpl
    ```
    
        - On lit à partir de ce fichier text en utilisant la classe scanner


     ```JAVA
        package pres;
        import dao.IDao;
        import metier.IMetier;

        import java.io.File;
        import java.lang.reflect.Method;
        import java.util.Scanner;
        public class Presentation2 {
            public static void main(String[] args) throws Exception{
                Scanner scanner=new Scanner(new File("config.txt"));
                String daoClasseName=scanner.nextLine();
                Class cDao=Class.forName(daoClasseName);
                IDao dao=(IDao) cDao.newInstance();
                System.out.println(dao.getData());

                String metierClasseName=scanner.nextLine();
                Class cMetier=Class.forName(metierClasseName);
                IMetier metier=(IMetier) cMetier.newInstance();
                Method method=cMetier.getMethod("setDao", IDao.class);
                method.invoke(metier,dao);
                System.out.println("Resultat=>"+metier.calcul());
            }
        }

    ```
    
    
                
                
    ## En utilisant le Framework Spring
        - On ajoute les depandances dans le fichier pom.xml
        ```XML
        <?xml version="1.0" encoding="UTF-8"?>
        <project xmlns="http://maven.apache.org/POM/4.0.0"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
            <modelVersion>4.0.0</modelVersion>

            <groupId>ma.enset</groupId>
            <artifactId>TP1-suite</artifactId>
            <version>1.0-SNAPSHOT</version>

            <properties>
                <maven.compiler.source>17</maven.compiler.source>
                <maven.compiler.target>17</maven.compiler.target>
                <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            </properties>
            <dependencies>
               <dependency>
                    <groupId>org.springframework</groupId>
                    <artifactId>spring-core</artifactId>
                    <version>6.0.4</version>
                </dependency>
                <dependency>
                    <groupId>org.springframework</groupId>
                    <artifactId>spring-context</artifactId>
                    <version>6.0.4</version>
                </dependency>
                <dependency>
                    <groupId>org.springframework</groupId>
                    <artifactId>spring-beans</artifactId>
                    <version>6.0.4</version>
                </dependency>
            </dependencies>

        </project>
        
        ```
        
        ## Version XML
        
                - On crée un fichier applicationContext.xml dans ressources
                
        ```XML
                <?xml version="1.0" encoding="UTF-8"?>
                <beans xmlns="http://www.springframework.org/schema/beans"
                       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
                    <bean id="dao" class="ext.DaoImpl2"></bean>
                    <bean id="metier" class="metier.MetierImpl">
                        <property name="dao" ref="dao"></property>
                    </bean>
                </beans>
        ```
        
        
     ```JAVA
        package pres;

        import metier.IMetier;
        import org.springframework.context.ApplicationContext;
        import org.springframework.context.support.ClassPathXmlApplicationContext;

        public class PresSpringXml {
            public static void main(String[] args) {
                ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
                IMetier metier=(IMetier) context.getBean("metier");
                System.out.println("Resultat =>"+metier.calcul());
            }
        }

    ```
               
    
        ## Version annotations
        
        
     ```JAVA
        package pres;

        import metier.IMetier;
        import org.springframework.context.ApplicationContext;
        import org.springframework.context.annotation.AnnotationConfigApplicationContext;

        public class PresSpringAnnotation {
            public static void main(String[] args) {
                ApplicationContext context=new AnnotationConfigApplicationContext("dao","metier");
                IMetier metier=context.getBean(IMetier.class);
                System.out.println(metier.calcul());
            }
        }

    ```
             
 
