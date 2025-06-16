const fs = require('fs');
const sharp = require('sharp');
const path = require('path');

const inputFolder = 'D:\\svgs1'; 
const outputFolder = 'D:\\svgs2'; 

// Function to convert SVG to JPG
const convertSvgToJpg = (inputFile, outputFile) => {
  sharp(inputFile)
    .png()
    // .jpeg({ quality: 90 }) // Set the quality for the output JPG
    .toFile(outputFile)
    .then(() => {
      console.log(`Converted `, count++, ` ${inputFile}`);
    })
    .catch(err => {
      console.error(`Error converting ${inputFile}:`, err);
    });
};

var count = 1;

// Recursive function to read directory contents
const readDirectory = (dir) => {
  fs.readdir(dir, { withFileTypes: true }, (err, files) => {
    if (err) {
      console.error('Error reading the directory', err);
      return;
    }

    files.forEach(file => {
      const fullPath = path.join(dir, file.name);

      if (file.isDirectory()) {

        // If the item is a directory, read its contents recursively
        readDirectory(fullPath);

      } else if (path.extname(file.name).toLowerCase() === '.svg') {

        // If the item is an SVG file, convert it
        const relativePath = path.relative(inputFolder, fullPath);

        const outputFile = path.join(outputFolder, relativePath).replace('.svg', '.jpg');

        fs.mkdirSync(path.dirname(outputFile), { recursive: true }); // Ensure the output directory exists

        convertSvgToJpg(fullPath, outputFile);
      }
    });
  });
};

// Start the conversion process
readDirectory(inputFolder);