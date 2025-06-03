# Insurance Information Viewer

This project is part of a practical activity for the **"Lenguaje de Marcas"** (Markup Languages) course. It demonstrates how to transform and display insurance data stored in **XML** format using **XSLT**, and how to present it in a styled HTML page with **CSS**.

## 📁 Project Structure

/
├── index.html # Static version of the insurance tables
├── seguro.xml # Main XML file containing policy data
├── seguro.xsl # XSLT file used to transform XML into HTML
├── seguro.css # CSS file used to style the tables and layout

## 📄 Description

The XML file contains detailed insurance policy information, including policyholder names, insured people, and coverage (life, accident, dental). The data is transformed into structured HTML tables by the XSL file, grouped by type of insurance.

The `index.html` file replicates the structure of the transformed output for visual comparison or fallback usage.

## 💡 Technologies Used

- **XML**: to store structured data about insurance policies.
- **XSLT**: to transform the XML into HTML dynamically.
- **HTML5**: to build a basic fallback static version.
- **CSS3**: to apply styles to the generated HTML tables.

## 🔍 Features

- Organized display by insurance type: **Accidents**, **Life**, and **Dental**.
- Polished, readable tables using border collapse and padding.
- Responsive, clean design using CSS.
- Dynamic XML rendering through the processing instruction:
  ```xml
  <?xml-stylesheet type="text/xsl" href="seguro.xsl"?>
## 🌐 Usage
To see the dynamic transformation, open seguro.xml in a web browser that supports XSLT processing (e.g., Firefox or Internet Explorer).

To view the static version, open index.html.

## 👩‍💻 Author
Developed by Alexandra V. as part of a class assignment in the 2023–2024 academic year.
