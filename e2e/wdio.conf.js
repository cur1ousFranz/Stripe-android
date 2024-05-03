exports.config = {
    runner: 'local',
    port: 4723,
    specs: [
        './features/**/*.feature'
    ],
    // Patterns to exclude.
    exclude: [
        // 'path/to/excluded/files'
    ],
    maxInstances: 10,
    capabilities: [{
        automationName: 'UiAutomator2',
        platformName: 'Android',
        browserName: '',
        autoGrantPermissions: false,
        allowTestPackages: true,

        // Timeouts (Seconds)
        newCommandTimeout: 60,
        deviceReadyTimeout: 60,
        androidDeviceReadyTimeout: 60,
        // Timeouts (Milliseconds)
        adbExecTimeout: 60000,
        androidInstallTimeout: 90000,
        appWaitDuration: 60000,
        autoWebviewTimeout: 60000,
        avdLaunchTimeout: 60000,
        avdReadyTimeout: 120000,
        uiautomator2ServerLaunchTimeout: 60000,
        uiautomator2ServerInstallTimeout: 60000
    }],
    bail: 0,
    waitforTimeout: 10000,
    connectionRetryTimeout: 120000,
    connectionRetryCount: 3,
    services: ['appium'],
    appium: {
        command: './node_modules/.bin/appium',
    },
    framework: 'cucumber',
    reporters: ['spec','junit'],

    // If you are using Cucumber you need to specify the location of your step definitions.
    cucumberOpts: {
        // <string[]> (file/dir) require files before executing features
        require: ['./features/step-definitions/steps.js'],
        // <boolean> show full backtrace for errors
        backtrace: false,
        // <string[]> ("extension:module") require files with the given EXTENSION after requiring MODULE (repeatable)
        requireModule: [],
        // <boolean> invoke formatters without executing steps
        dryRun: false,
        // <boolean> abort the run on first failure
        failFast: false,
        // <string[]> Only execute the scenarios with name matching the expression (repeatable).
        name: [],
        // <boolean> hide step definition snippets for pending steps
        snippets: true,
        // <boolean> hide source uris
        source: true,
        // <boolean> fail if there are any undefined or pending steps
        strict: false,
        // <string> (expression) only execute the features or scenarios with tags matching the expression
        tagExpression: '',
        // <number> timeout for step definitions
        timeout: 60000,
        // <boolean> Enable this config to treat undefined definitions as warnings.
        ignoreUndefinedDefinitions: false
    },
}
