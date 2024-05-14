# react-native-jsonwebtoken
NOTE:  Currently, this library supports only android systems! For IOS, It is coming soon ...
React Native component to verify the signature of a JWT string representation/token using public_key, generate a SecretKey, encrypt Data, ... Others features are coming soon.

## Installation

```sh
npm install react-native-jsonwebtoken
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

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
