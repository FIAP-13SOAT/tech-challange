package com.fiapchallenge.garage.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

class AnnotationRulesTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setUp() {
        classes = new ClassFileImporter().importPackages("com.fiapchallenge.garage");
    }

    @Test
    void controllersShouldBeAnnotatedWithRestController() {
        ArchRule rule = classes()
                .that().resideInAPackage("..adapters.inbound.controller..")
                .and().haveSimpleNameEndingWith("Controller")
                .and().areNotInterfaces()
                .should().beAnnotatedWith("org.springframework.web.bind.annotation.RestController");

        rule.check(classes);
    }

    @Test
    void servicesShouldBeAnnotatedWithService() {
        ArchRule rule = classes()
                .that().resideInAPackage("..application..")
                .and().haveSimpleNameEndingWith("Service")
                .and().areNotInterfaces()
                .should().beAnnotatedWith("org.springframework.stereotype.Service")
                .allowEmptyShould(true);

        rule.check(classes);
    }

    @Test
    void repositoryImplementationsShouldBeAnnotatedWithRepository() {
        ArchRule rule = classes()
                .that().resideInAPackage("..adapters.outbound.repositories..")
                .and().haveSimpleNameEndingWith("Repository")
                .and().areNotInterfaces()
                .should().beAnnotatedWith("org.springframework.stereotype.Repository")
                .allowEmptyShould(true);

        rule.check(classes);
    }

    @Test
    void configurationClassesShouldBeAnnotatedWithConfiguration() {
        ArchRule rule = classes()
                .that().resideInAPackage("..config..")
                .and().haveSimpleNameEndingWith("Config")
                .should().beAnnotatedWith("org.springframework.context.annotation.Configuration");

        rule.check(classes);
    }

    @Test
    void entitiesShouldBeAnnotatedWithEntity() {
        ArchRule rule = classes()
                .that().resideInAPackage("..adapters.outbound.entities..")
                .and().areNotInterfaces()
                .and().areNotEnums()
                .and().areNotAnnotations()
                .and().areNotMemberClasses()
                .and().doNotHaveSimpleName("ServiceOrderServiceTypeId")
                .should().beAnnotatedWith("jakarta.persistence.Entity")
                .allowEmptyShould(true);

        rule.check(classes);
    }

    @Test
    void domainModelsShouldNotHaveSpringAnnotations() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().beAnnotatedWith("org.springframework.stereotype.Service")
                .orShould().beAnnotatedWith("org.springframework.stereotype.Repository")
                .orShould().beAnnotatedWith("org.springframework.stereotype.Component")
                .orShould().beAnnotatedWith("org.springframework.web.bind.annotation.RestController");

        rule.check(classes);
    }

    @Test
    void applicationServicesShouldNotHaveRepositoryAnnotation() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..application..")
                .should().beAnnotatedWith("org.springframework.stereotype.Repository");

        rule.check(classes);
    }

    @Test
    void controllersShouldNotHaveServiceAnnotation() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..adapters.inbound.controller..")
                .should().beAnnotatedWith("org.springframework.stereotype.Service");

        rule.check(classes);
    }
}