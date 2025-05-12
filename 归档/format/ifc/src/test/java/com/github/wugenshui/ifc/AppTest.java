package com.github.wugenshui.ifc;

import com.apstex.ifc2x3toolbox.ifc2x3.IfcProject;
import com.apstex.ifc2x3toolbox.ifcmodel.IfcModel;
import com.apstex.step.core.ClassInterface;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Collection;

/**
 * @author : chenbo
 * @date : 2022-02-15
 */
public class AppTest {
    @Test
    public void apiTest() throws Exception {
        // create a new instance of IfcModel
        IfcModel ifcModel = new IfcModel();
        // load an IFC STEP file from the file system
        ClassPathResource resource = new ClassPathResource("construction.ifc");
        File stepFile = resource.getFile();
        ifcModel.readStepFile(stepFile);
        System.out.println("stepFile = " + stepFile);

        IfcProject ifcProject = ifcModel.getIfcProject();
        System.out.println("ifcProject = " + ifcProject);

        Collection<ClassInterface> ifcObjects = ifcModel.getIfcObjects();
        System.out.println("ifcObjects = " + ifcObjects);
    }
}
