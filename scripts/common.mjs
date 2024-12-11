//@ts-check
import {parseArgs as node_parseArgs} from "node:util";
import {join, dirname} from "node:path";
export const TEMPLATE_FILE = "./scripts/template"
const BASE_PATH = "./src/main/kotlin/solutions"

/**
 *
 * @param year {string}
 * @param day {string}
 * @returns {string}
 */
export function getTodaysFilename(year, day) {
    return join(BASE_PATH, `y${year}/d${day}/Code.kt`)
}
export function getTodaysInputFilename(year, day) {
    return join(BASE_PATH, `y${year}/d${day}/input.txt`)
}
/**
 *
 * @param year {string}
 * @param day {string}
 * @returns {string}
 */
export function getTodaysDir(year, day) {
    return dirname(getTodaysFilename(year, day))
}

export function getToday() {
    const d = new Date();

    return {
        year: d.getFullYear().toString().substring(2),
        day: (d.getDate()).toString()
    }
}

/**
 * @typedef {Record<"year"|"day"|"filename"|"dir", string>} opts
 */
/**
 *
 * @returns {opts}
 */
export function parseArgs() {
    /**
     * @type {import("util").ParseArgsConfig["options"]}
     */
    const options = {
        year: {
            type: "string",
            default: getToday().year,
            short: "y"
        },
        day: {
            type: "string",
            default: getToday().day,
            short: "d"
        }
    }
    const {year, day} = node_parseArgs({args: process.argv.slice(2), options}).values;
    return {
        year, day,
        filename: getTodaysFilename(year, day),
        dir: getTodaysDir(year, day),
        inputFilename: getTodaysInputFilename(year, day)
    }
}