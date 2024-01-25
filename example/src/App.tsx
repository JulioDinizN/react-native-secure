import * as React from 'react';

import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import {
  multiply,
  canAuthenticate,
  requireLocalAuth,
} from 'react-native-secure';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();

  React.useEffect(() => {
    multiply(3, 7).then(setResult);
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
      <TouchableOpacity
        onPress={() => {
          canAuthenticate();
        }}
      >
        <Text>Can authenticate?</Text>
      </TouchableOpacity>
      <View style={styles.separator} />
      <TouchableOpacity
        onPress={() => {
          requireLocalAuth();
        }}
      >
        <Text>Authenticate</Text>
      </TouchableOpacity>
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
  separator: {
    height: 50,
  },
});
