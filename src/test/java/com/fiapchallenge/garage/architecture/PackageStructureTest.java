package com.fiapchallenge.garage.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

class PackageStructureTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setUp() {
        classes = new ClassFileImporter().importPackages("com.fiapchallenge.garage");
    }

    @Test
    void domainModelsShouldResideInDomainPackage() {
        ArchRule rule = classes()
                .that().areNotInterfaces()
                .and().areNotEnums()
                .and().areNotAnnotations()
                .and().resideInAPackage("..domain..")
                .and().areNotAssignableTo(Exception.class)
                .should().resideInAPackage("..domain..");

        rule.check(classes);
    }

    @Test
    void useCasesShouldResideInApplicationPackage() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("UseCase")
                .should().resideInAPackage("..application..");

        rule.check(classes);
    }

    @Test
    void controllersShouldResideInControllerPackage() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("Controller")
                .should().resideInAPackage("..adapters.inbound.controller..");

        rule.check(classes);
    }

    @Test
    void repositoryInterfacesShouldResideInDomainPackage() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("Repository")
                .and().areInterfaces()
                .and().haveSimpleNameNotStartingWith("Jpa")
                .should().resideInAPackage("..domain..");

        rule.check(classes);
    }

    @Test
    void repositoryImplementationsShouldResideInOutboundPackage() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("Repository")
                .and().areNotInterfaces()
                .should().resideInAPackage("..adapters.outbound.repositories..")
                .allowEmptyShould(true);

        rule.check(classes);
    }

    @Test
    void entitiesShouldResideInEntitiesPackage() {
        ArchRule rule = classes()
                .that().areAnnotatedWith("jakarta.persistence.Entity")
                .should().resideInAPackage("..adapters.outbound.entities..")
                .allowEmptyShould(true);

        rule.check(classes);
    }

    @Test
    void dtosShouldResideInDtoPackage() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("DTO")
                .should().resideInAPackage("..dto..");

        rule.check(classes);
    }

    @Test
    void mappersShouldResideInMapperPackage() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("Mapper")
                .should().resideInAPackage("..mapper..");

        rule.check(classes);
    }

    @Test
    void exceptionsShouldResideInExceptionPackage() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("Exception")
                .and().areAssignableTo(Exception.class)
                .should().resideInAPackage("..exception..");

        rule.check(classes);
    }

    @Test
    void configurationClassesShouldResideInConfigPackage() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("Config")
                .should().resideInAPackage("..config..");

        rule.check(classes);
    }
}