package de.hhu.propra.thesis.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(
    packages = "de.hhu.propra.thesis",
    importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class ArchUnitTest {
  @ArchTest
  static final ArchRule services_should_be_annotated =
      classes().that()
          .resideInAPackage("..applicationlayer.service..")
          .should().beAnnotatedWith(Service.class);

  @ArchTest
  static final ArchRule controllers_should_be_annotated =
      classes().that()
          .resideInAPackage("..controller..")
          .should().beAnnotatedWith(Controller.class);
  @ArchTest
  static final ArchRule repositories_should_be_annotated =
      classes().that()
          .resideInAPackage("..infrastructurelayer.reposimplementation..")
          .should().beAnnotatedWith(Repository.class);

  @ArchTest
  static final ArchRule exam_aggregate_should_not_be_accessed =
      noClasses().that()
          .resideOutsideOfPackage("..domain.model.examagg..")
          .should().accessClassesThat()
          .resideInAPackage("..domain.model.examagg..");


  @ArchTest
  static final ArchRule onion_architecture =
      onionArchitecture()
          .domainModels("..domain.model..")
          .domainServices("..domain.repository..")
          .applicationServices("..applicationlayer..")
          .adapter("web", "..controller..")
          .adapter("config", "..config..")
          .adapter("db", "..infrastructurelayer..");
}
