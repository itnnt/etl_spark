How to configure “Shorten command line” method for whole project in IntelliJ
----------------
Inside your .idea folder, change workspace.xml file
Add
```
<property name="dynamic.classpath" value="true" />
```
to 
```
 <component name="PropertiesComponent">
.
.
.
  </component>
```
Example
```
 <component name="PropertiesComponent">
    <property name="project.structure.last.edited" value="Project" />
    <property name="project.structure.proportion" value="0.0" />
    <property name="project.structure.side.proportion" value="0.0" />
    <property name="settings.editor.selected.configurable" value="preferences.pluginManager" />
    <property name="dynamic.classpath" value="true" />
  </component>
```
If you don't see one, feel free to add it yourself
```
<component name="PropertiesComponent">
    <property name="dynamic.classpath" value="true" />
</component>
```