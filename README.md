# BlockChain
A basic infrastructure to depict BlockChain Implementation in accordance with its basic utility and features. 
Each Block contains its unique hashcode, along with the hashcode of the previous block to maintain a chain. Each block contains a nonce value, which is necessary for cracking the codes for miners to win lotteries. For simplicity, we have used valid hashcodes that begin with '215'. (Just a random key for validating hashcodes used by our blockchain). The miner which gets a hash (using nonce for randomness) with the first three charachters equal to 215 wins the lottery and mines the block.
#Online Market Place

We have created an online market system. The users and miners for the blockchain are entered manually for convenience. A unique pair of public/private keys is assigned to each created user in order to facilitate communication, and digtal signatures. 
Once a user is created, the user can be directed to the marketplace, where he can buy/sell items. Items are posted as listings on the Market Menu.
When a user chooses to buy a particular product, a transaction is initiated and is put into Unverified Transactions list.
The Transaction state is set to pending until and unless the transaction block is mined by a miner. Once that is done, each user associated with each transaction is informed about the confirmation of his transaction, which can be viewed in the Blockchain itself. Once the block is added to chain, only then the actual money transfer occurs from payer's wallet to payee's wallet.

#Verification/Security

We manually choose to start the mining process, i.e. a naive implementation of the fact that BitCoin aims to create/add 1 block to the blockchain every 10 minutes.
This helps to hold potential attackers from manipulating the entire blockchain at once. This allows us to achieve immutability. 
We have adopted the Proof of Work methodology. All pending transactions are fetched by all the potential miners, who then try to win the lottery by cracking the hash code using various nonce values. The miner who successfully cracks the code gets the reward, and his block is added to the BlockChain.
The latest block added to the chain is then verified by various miners, and if 50% of the miners are able to verify it, the block is considered valid. Each miner verifies the block by verifying the digital signature of the sender for each of the transactions contained in the block.  
