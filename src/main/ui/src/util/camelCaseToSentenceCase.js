export function camelCaseToSentenceCase(string) {
    const camelCase =string.replace(/([a-z])([A-Z])/g, '$1 $2').split(" ");

    let sentence = camelCase[0].charAt(0).toUpperCase() + camelCase[0].substring(1);

    for (let i = 1; i < camelCase.length; i++) {
        sentence += " " + camelCase[i].toLowerCase();
    }

    return sentence;
}