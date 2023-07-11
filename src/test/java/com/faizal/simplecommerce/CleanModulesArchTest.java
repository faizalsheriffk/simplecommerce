package com.faizal.simplecommerce;

import com.faizal.simplecommerce.common.events.DomainEvent;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.NESTED_CLASSES;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.assignableTo;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideOutsideOfPackages;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class CleanModulesArchTest {

    @Test
    void sales_catalog_service_has_no_dependency_on_others() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.faizal.simplecommerce.sales.catalog");
        ArchRule rule = classes().should().onlyDependOnClassesThat(
                resideOutsideOfPackages(
                        "com.faizal.simplecommerce.."
                ).or(resideInAPackage("com.faizal.simplecommerce.sales.catalog.."
                ).or(resideInAPackage("com.faizal.simplecommerce.common.."))));
        rule.check(importedClasses);
    }

    @Test
    void sales_catalog_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.faizal.simplecommerce.sales.catalog");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.faizal.simplecommerce.sales.catalog.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.faizal.simplecommerce.sales.catalog.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void sales_order_service_has_no_dependencies_on_others_except_events() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.faizal.simplecommerce.sales.order");
        ArchRule rule = classes().should().onlyDependOnClassesThat(
                resideOutsideOfPackages(
                        "com.faizal.simplecommerce.."
                ).or(resideInAPackage("com.faizal.simplecommerce.sales.order.."
                ).or(resideInAPackage("com.faizal.simplecommerce.common..")
                ).or(assignableTo(DomainEvent.class).or(NESTED_CLASSES))));
        rule.check(importedClasses);
    }

    @Test
    void sales_order_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.faizal.simplecommerce.sales.order");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.faizal.simplecommerce.sales.order.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.faizal.simplecommerce.sales.order.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void sales_cart_service_has_no_dependency_on_others() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.faizal.simplecommerce.sales.cart");
        ArchRule rule = classes().should().onlyDependOnClassesThat(
                resideOutsideOfPackages(
                        "com.faizal.simplecommerce.."
                ).or(resideInAPackage("com.faizal.simplecommerce.sales.cart.."
                ).or(resideInAPackage("com.faizal.simplecommerce.common.."))));
        rule.check(importedClasses);
    }

    @Test
    void billing_payment_service_has_no_dependencies_on_others_except_events() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.faizal.simplecommerce.billing.payment");
        ArchRule rule = classes().should().onlyDependOnClassesThat(
                resideOutsideOfPackages(
                        "com.faizal.simplecommerce.."
                ).or(resideInAPackage("com.faizal.simplecommerce.billing.payment.."
                ).or(resideInAPackage("com.faizal.simplecommerce.common..")
                ).or(assignableTo(DomainEvent.class).or(NESTED_CLASSES))));
        rule.check(importedClasses);
    }

    @Test
    void billing_payment_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.faizal.simplecommerce.billing.payment");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.faizal.simplecommerce.billing.payment.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.faizal.simplecommerce.billing.payment.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void shipping_delivery_service_has_no_dependencies_on_others_except_events() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.faizal.simplecommerce.payments.psp.commercehub");
        ArchRule rule = classes().should().onlyDependOnClassesThat(
                resideOutsideOfPackages(
                        "com.faizal.simplecommerce.."
                ).or(resideInAPackage("com.faizal.simplecommerce.payments.psp.commercehub.."
                ).or(resideInAPackage("com.faizal.simplecommerce.common..")
                ).or(assignableTo(DomainEvent.class).or(NESTED_CLASSES))));
        rule.check(importedClasses);
    }

    @Test
    void shipping_delivery_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.faizal.simplecommerce.payments.psp.commercehub");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.faizal.simplecommerce.payments.psp.commercehub.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.faizal.simplecommerce.payments.psp.commercehub.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void shipping_dispatching_service_has_no_dependencies_on_others_except_delivery_and_events() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.faizal.simplecommerce.payments.mastertransactions");
        ArchRule rule = classes().should().onlyDependOnClassesThat(
                resideOutsideOfPackages(
                        "com.faizal.simplecommerce.."
                ).or(resideInAPackage("com.faizal.simplecommerce.payments.mastertransactions.."
                ).or(resideInAPackage("com.faizal.simplecommerce.payments.psp.commercehub..")
                ).or(resideInAPackage("com.faizal.simplecommerce.common..")
                ).or(assignableTo(DomainEvent.class).or(NESTED_CLASSES))));
        rule.check(importedClasses);
    }

    @Test
    void shipping_dispatching_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.faizal.simplecommerce.payments.mastertransactions");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.faizal.simplecommerce.payments.mastertransactions.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.faizal.simplecommerce.payments.mastertransactions.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void warehouse_service_has_no_dependencies_on_others_except_events() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.faizal.simplecommerce.payments.orchestrator");
        ArchRule rule = classes().should().onlyDependOnClassesThat(
                resideOutsideOfPackages(
                        "com.faizal.simplecommerce.."
                ).or(resideInAPackage("com.faizal.simplecommerce.payments.orchestrator.."
                ).or(resideInAPackage("com.faizal.simplecommerce.common..")
                ).or(assignableTo(DomainEvent.class).or(NESTED_CLASSES))));
        rule.check(importedClasses);
    }

    @Test
    void warehouse_domain_has_no_dependency_to_its_implementation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.faizal.simplecommerce.payments.orchestrator");
        ArchRule rule = classes()
                .that().resideOutsideOfPackages(
                        "com.faizal.simplecommerce.payments.orchestrator.jdbc..")
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.faizal.simplecommerce.payments.orchestrator.jdbc..");
        rule.check(importedClasses);
    }

    @Test
    void catalog_service_has_no_dependencies_on_billing() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.faizal.simplecommerce.admin.portal");
        ArchRule rule = classes()
                .should().onlyDependOnClassesThat().resideOutsideOfPackages(
                        "com.faizal.simplecommerce.billing..");
        rule.check(importedClasses);
    }

    @Test
    void portal_web_uses_only_its_own_use_cases_and_no_direct_dependencies_on_other_services() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages(
                "com.faizal.simplecommerce.sales",
                "com.faizal.simplecommerce.payments.orchestrator",
                "com.faizal.simplecommerce.shipping",
                "com.faizal.simplecommerce.billing");
        ArchRule rule = classes()
                .should().onlyHaveDependentClassesThat().resideOutsideOfPackage(
                        "com.faizal.simplecommerce.admin.portal.web..");
        rule.check(importedClasses);
    }
}
