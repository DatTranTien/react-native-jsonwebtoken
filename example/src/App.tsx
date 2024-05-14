//@ts-ignore
import React, { useEffect, useState } from 'react';
import { StyleSheet, View, Text } from 'react-native';
import { verifyToken, generateSecretKey, encryptData } from 'react-native-jsonwebtoken';
import { PUBLIC_KEY, TOKEN, data } from './dataSample';



const App: React.FC = () => {
  const [result, setResult] = useState<string>();
  const [encryptionResult, setEncryptionResult] = useState<EncryptionResult>();
  const [encryptedData, setEncryptedData] = useState<string>();

  useEffect(() => {
    verifyToken(TOKEN, PUBLIC_KEY)
      .then((verificationResult: boolean) => {
        console.log('Verification Result:', verificationResult);
        setResult('Token is valid');
      })
      .catch((error: ErrorWithMessage) => {
        console.error('Error verifying JWT:', error.message);
        setResult(`Error verifying JWT: ${error.message}`);
      });

    generateSecretKey()
      .then((result: EncryptionResult) => {
        console.log("Generated Key:", result.key);
        console.log("Generated IV:", result.iv);
        setEncryptionResult(result)
      })
      .catch((error: ErrorWithMessage) => {
        console.error("Error generating secret key:", error.message);
        setEncryptionResult(`Error generating secret key: ${error.message}`)
      });

    const dataToEncrypt = JSON.stringify(data);

    encryptData(dataToEncrypt, PUBLIC_KEY)
      .then((encrypted: string) => {
        console.log('Encrypted Data:', encrypted);
        setEncryptedData(`Encrypted Data: ${encrypted}`)
      })
      .catch((error: ErrorWithMessage) => {
        console.error('Error encrypting data:', error.message);
        setEncryptedData(`Error encrypting data: ${error.message}`)
      });

  }, []);

  return (
    <View style={styles.container}>
      <View style={{flex:0.3}}>
      <Text>{result || "Loading result..."}</Text>
      </View>
      <View style={{flex:0.3}}>
      <Text>{encryptionResult?.key + encryptionResult?.iv|| "Loading encryptionResult..."}</Text>
      </View>
      <View style={{flex:0.3}}>
      <Text>{encryptedData || "Loading encryptedData..."}</Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});

export default App;
