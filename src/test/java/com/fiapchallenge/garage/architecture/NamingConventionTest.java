package com.fiapchallenge.garage.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

class NamingConventionTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setUp() {
        classes = new ClassFileImporter().importPackages("com.fiapchallenge.garage");
    }

    @Test
    void controllersShouldHaveControllerSuffix() {
        ArchRule rule = classes()
                .that().resideInAPackage("..adapters.inbound.controller..")
                .and().areNotInterfaces()
                .and().areNotEnums()
                .and().areNotAnnotations()
                .should().haveSimpleNameEndingWith("Controller");

        rule.check(classes);
    }

    @Test
    void useCasesShouldHaveUseCaseSuffix() {
        ArchRule rule = classes()
                .that().resideInAPackage("..application..")
                .and().areInterfaces()
                .should().haveSimpleNameEndingWith("UseCase");

        rule.check(classes);
    }

    @Test
    void servicesShouldHaveServiceSuffix() {
        ArchRule rule = classes()
                .that().resideInAPackage("..application..")
                .and().areNotInterfaces()
                .and().areNotEnums()
                .and().areNotAnnotations()
                .and().areNotRecords()
                .should().haveSimpleNameEndingWith("Service");

        rule.check(classes);
    }

    @Test
    void repositoriesShouldHaveRepositorySuffix() {
        ArchRule rule = classes()
                .that().resideInAPackage("..domain..")
                .and().areInterfaces()
                .and().haveSimpleNameContaining("Repository")
                .should().haveSimpleNameEndingWith("Repository");

        rule.check(classes);
    }

    @Test
    void entitiesShouldHaveEntitySuffix() {
        ArchRule rule = classes()
                .that().resideInAPackage("..adapters.outbound.entities..")
                .and().areNotInterfaces()
                .and().areNotEnums()
                .and().areNotAnnotations()
                .should().haveSimpleNameEndingWith("Entity")
                .allowEmptyShould(true);

        rule.check(classes);
    }

    @Test
    void dtosShouldHaveDtoSuffix() {
        ArchRule rule = classes()
                .that().resideInAPackage("..dto..")
                .and().areNotInterfaces()
                .and().areNotEnums()
                .and().areNotAnnotations()
                .should().haveSimpleNameEndingWith("DTO");

        rule.check(classes);
    }

    @Test
    void mappersShouldHaveMapperSuffix() {
        ArchRule rule = classes()
                .that().resideInAPackage("..mapper..")
                .and().areNotInterfaces()
                .and().areNotEnums()
                .and().areNotAnnotations()
                .should().haveSimpleNameEndingWith("Mapper");

        rule.check(classes);
    }

    @Test
    void exceptionsShouldHaveExceptionSuffix() {
        ArchRule rule = classes()
                .that().resideInAPackage("..exception..")
                .and().areNotInterfaces()
                .and().areNotEnums()
                .and().areNotAnnotations()
                .should().haveSimpleNameEndingWith("Exception");

        rule.check(classes);
    }
}