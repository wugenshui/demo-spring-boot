package com.github.wugenshui.ifc;

import cn.hutool.core.io.IoUtil;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.emf.IfcModelInterfaceException;
import org.bimserver.emf.PackageMetaData;
import org.bimserver.emf.Schema;
import org.bimserver.ifc.step.deserializer.Ifc2x3tc1StepDeserializer;
import org.bimserver.ifc.step.serializer.Ifc2x3tc1StepSerializer;
import org.bimserver.models.ifc2x3tc1.Ifc2x3tc1Package;
import org.bimserver.models.ifc2x3tc1.IfcBuilding;
import org.bimserver.models.ifc2x3tc1.IfcElementQuantity;
import org.bimserver.models.ifc2x3tc1.IfcQuantityVolume;
import org.bimserver.models.ifc2x3tc1.IfcRelDefinesByProperties;
import org.bimserver.plugins.PluginConfiguration;
import org.bimserver.plugins.deserializers.DeserializeException;
import org.bimserver.plugins.serializers.SerializerException;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Paths;

/**
 * @author : chenbo
 * @date : 2022-02-15
 */
public class IfcApp {
    public static void main(String[] args) {
        Ifc2x3tc1StepDeserializer deserializer = new Ifc2x3tc1StepDeserializer();
        PackageMetaData packageMetaData = new PackageMetaData(Ifc2x3tc1Package.eINSTANCE, Schema.IFC2X3TC1, Paths.get("tmp"));
        deserializer.init(packageMetaData);

        try {
            // 读取ifc文件
            ClassPathResource resource = new ClassPathResource("construction.ifc");
            InputStream inputStream = resource.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IoUtil.copy(inputStream, outputStream);
            IfcModelInterface model = deserializer.read(new ByteArrayInputStream(outputStream.toByteArray()), "", outputStream.size(), null);

            // This is needed so we start with a clean slate of express id's
            model.resetExpressIds();

            // This is needed so we continue counting at highest already existing oid
            model.fixOidCounter();

            for (IfcBuilding building : model.getAllWithSubTypes(IfcBuilding.class)) {
                try {
                    // Use createAndAdd to actually add the object to the model
                    IfcQuantityVolume g_volume = model.createAndAdd(IfcQuantityVolume.class);
                    g_volume.setName("Test Quantity wodemingzibukenengzhemechang");
                    g_volume.setVolumeValue(1000000000);

                    IfcElementQuantity el_gv = model.createAndAdd(IfcElementQuantity.class);
                    el_gv.getQuantities().add(g_volume);

                    IfcRelDefinesByProperties ifcRelDefinesByProperties1 = model.createAndAdd(IfcRelDefinesByProperties.class);
                    ifcRelDefinesByProperties1.setRelatingPropertyDefinition(el_gv);
                    ifcRelDefinesByProperties1.getRelatedObjects().add(building);
                    building.getIsDefinedBy().add(ifcRelDefinesByProperties1);
                } catch (IfcModelInterfaceException e) {
                    e.printStackTrace();
                }
            }

            Ifc2x3tc1StepSerializer serializer = new Ifc2x3tc1StepSerializer(new PluginConfiguration());
            // Put "true" as the last argument in order to generate new express id's
            serializer.init(model, null, true);
            serializer.writeToFile(Paths.get("output.ifc"), null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DeserializeException e) {
            e.printStackTrace();
        } catch (SerializerException e) {
            e.printStackTrace();
        }

    }
}
