# react-native-jsonwebtoken
NOTE:  Currently, this library supports only ANDROID systems! For IOS, It is coming soon ...
React Native component to verify the signature of a JWT string representation/token using public_key, generate a SecretKey, encrypt Data, ... Others features are coming soon.

## Installation

```bash
# using npm
npm install react-native-jsonwebtoken 


# OR using Yarn
yarn add react-native-jsonwebtoken 
```

### Prerequisites
Before start, please add this below config in app/build.grale to resolve conflict Android library
```
configurations {
    all {
        exclude group: 'org.bouncycastle', module: 'bcprov-jdk15to18'
    }
}
```
## Usage

```js
import { verifyToken, generateSecretKey, encryptData } from 'react-native-jsonwebtoken';

// Handle for verify token using a public_key

const result = verifyToken(TOKEN, PUBLIC_KEY)
      .then((verificationResult: boolean) => {
        console.log('Verification Result:', verificationResult);
        setResult('Token is valid');
      })
      .catch((error: ErrorWithMessage) => {
        console.error('Error verifying JWT:', error.message);
        setResult(`Error verifying JWT: ${error.message}`);
      });

// Generate a secret key by using generateSecretKey

const encryptionResult = generateSecretKey()
      .then((result: EncryptionResult) => {
        console.log("Generated Key:", result.key);
        console.log("Generated IV:", result.iv);
        setEncryptionResult(result)
      })
      .catch((error: ErrorWithMessage) => {
        console.error("Error generating secret key:", error.message);
        setEncryptionResult(`Error generating secret key: ${error.message}`)
      });

//Encrypted Data
const dataToEncrypt = JSON.stringify(data);
const encryptedData = encryptData(dataToEncrypt, PUBLIC_KEY)
      .then((encrypted: string) => {
        console.log('Encrypted Data:', encrypted);
        setEncryptedData(`Encrypted Data: ${encrypted}`)
      })
      .catch((error: ErrorWithMessage) => {
        console.error('Error encrypting data:', error.message);
        setEncryptedData(`Error encrypting data: ${error.message}`)
      });
```
### Now what?

- If you want to add this new React Native code to an existing application, check out the [Integration guide](https://reactnative.dev/docs/integration-with-existing-apps).
- If you're curious to learn more about React Native, check out the [Introduction to React Native](https://reactnative.dev/docs/getting-started).

# Troubleshooting

If you can't get this to work, see the [Troubleshooting](https://reactnative.dev/docs/troubleshooting) page.

# Learn More

To learn more about React Native, take a look at the following resources:

- [React Native Website](https://reactnative.dev) - learn more about React Native.
- [Getting Started](https://reactnative.dev/docs/environment-setup) - an **overview** of React Native and how setup your environment.
- [Learn the Basics](https://reactnative.dev/docs/getting-started) - a **guided tour** of the React Native **basics**.
- [Blog](https://reactnative.dev/blog) - read the latest official React Native **Blog** posts.
- [`@facebook/react-native`](https://github.com/facebook/react-native) - the Open Source; GitHub **repository** for React Native.


## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
