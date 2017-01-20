package me.denyol.blockbank.proxy;

public class ServerProxy implements IBlockBankProxy
{

	@Override
	public void registerRenders()
	{} // do nothing, we dont render on a server

	@Override
	public void registerTESRs()
	{}

}
