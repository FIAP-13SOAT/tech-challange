package com.fiapchallenge.garage.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

class DependencyRulesTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setUp() {
        classes = new ClassFileImporter().importPackages("com.fiapchallenge.garage");
    }

    @Test
    void domainShouldNotDependOnApplication() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("..application..");

        rule.check(classes);
    }

    @Test
    void domainShouldNotDependOnAdapters() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("..adapters..");

        rule.check(classes);
    }

    @Test
    void domainShouldNotDependOnInfrastructure() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("..infra..");

        rule.check(classes);
    }

    @Test
    void domainShouldNotDependOnConfig() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAPackage("..config..");

        rule.check(classes);
    }

    @Test
    void applicationShouldNotDependOnConfig() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAPackage("..config..");

        rule.check(classes);
    }

    @Test
    void inboundAdaptersShouldNotDependOnOutboundAdapters() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..adapters.inbound..")
                .should().dependOnClassesThat().resideInAPackage("..adapters.outbound..");

        rule.check(classes);
    }

    @Test
    void outboundAdaptersShouldNotDependOnInboundAdapters() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..adapters.outbound..")
                .should().dependOnClassesThat().resideInAPackage("..adapters.inbound..");

        rule.check(classes);
    }

    @Test
    void controllersShouldNotDependOnRepositoryImplementations() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..adapters.inbound.controller..")
                .should().dependOnClassesThat().resideInAPackage("..adapters.outbound.repositories..");

        rule.check(classes);
    }

    @Test
    void controllersShouldNotDependOnEntities() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..adapters.inbound.controller..")
                .should().dependOnClassesThat().resideInAPackage("..adapters.outbound.entities..");

        rule.check(classes);
    }
}