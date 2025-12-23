package com.fiapchallenge.garage.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class CleanArchitectureTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setUp() {
        classes = new ClassFileImporter().importPackages("com.fiapchallenge.garage");
    }

    @Test
    void applicationShouldNotDependOnAdapters() {
        ArchRule applicationRule = noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAPackage("..adapters..");

        applicationRule.check(classes);
    }

    @Test
    void applicationShouldNotDependOnInfrastructure() {
        ArchRule applicationRule = noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAPackage("..infra..");

        applicationRule.check(classes);
    }

    @Test
    void repositoriesShouldBeInterfaces() {
        ArchRule repositoryRule = classes()
                .that().haveNameMatching(".*Repository")
                .and().resideInAPackage("..domain..")
                .should().beInterfaces();

        repositoryRule.check(classes);
    }

    @Test
    void useCasesShouldBeInterfaces() {
        ArchRule useCaseRule = classes()
                .that().haveNameMatching(".*UseCase")
                .should().beInterfaces();

        useCaseRule.check(classes);
    }

    @Test
    void servicesShouldImplementUseCases() {
        ArchRule serviceRule = classes()
                .that().haveNameMatching(".*Service")
                .and().resideInAPackage("..application..")
                .should().dependOnClassesThat().haveNameMatching(".*UseCase");

        serviceRule.check(classes);
    }

    @Test
    void controllersShouldOnlyDependOnUseCases() {
        ArchRule controllerRule = classes()
                .that().haveNameMatching(".*Controller")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        "..application..",
                        "..domain..",
                        "..shared..",
                        "..adapters.inbound.controller..",
                        "org.springframework..",
                        "jakarta.validation..",
                        "io.swagger.v3.oas.annotations..",
                        "java.."
                );

        controllerRule.check(classes);
    }

    @Test
    void entitiesShouldResideInOutboundPackage() {
        ArchRule entityRule = classes()
                .that().areAnnotatedWith("jakarta.persistence.Entity")
                .should().resideInAPackage("..adapters.outbound.entities..");

        entityRule.check(classes);
    }

    @Test
    void domainModelsShouldNotHaveJpaAnnotations() {
        ArchRule domainModelRule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().beAnnotatedWith("jakarta.persistence.Entity")
                .orShould().beAnnotatedWith("jakarta.persistence.Table")
                .orShould().beAnnotatedWith("jakarta.persistence.Id");

        domainModelRule.check(classes);
    }
}