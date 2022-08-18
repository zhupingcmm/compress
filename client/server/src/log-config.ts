import pkg from "log4js";
import path from "path";
const { configure } = pkg;

export const initLog = () => {
  const CMD = process.cwd();
  const configFile = path.join(CMD, "config", "log", "log4j.json");
  console.log('configFile:::', configFile)
  configure(configFile);
};
